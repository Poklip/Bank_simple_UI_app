package com.aeon.testapp.ui

import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.aeon.testapp.App
import com.aeon.testapp.R
import com.aeon.testapp.data.models.LoginInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val etLogin: EditText by lazy { requireActivity().findViewById(R.id.etLogin) }
    private val etPassword: EditText by lazy { requireActivity().findViewById(R.id.etPassword) }
    private val tvError: TextView by lazy { requireActivity().findViewById(R.id.tvError) }
    private val btnLogin: Button by lazy { requireActivity().findViewById(R.id.btnLogin) }

    private val networkScope = CoroutineScope(Dispatchers.IO)

    data class Answer(val marker: String, val message: String)

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val sharedPrefs = requireActivity().getSharedPreferences("SP", MODE_PRIVATE)

        super.onViewCreated(view, savedInstanceState)

        btnLogin.setOnTouchListener { _, event ->
            val loginInfo = LoginInfo(etLogin.text.toString(), etPassword.text.toString())
            Log.d("KVS_DEBUG", loginInfo.toString())
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    btnLogin.animate().scaleX(0.2f).scaleY(0.2f).setDuration(200L).start()
                    startLoggingIn(sharedPrefs, loginInfo)
                }
                MotionEvent.ACTION_UP -> {
                    btnLogin.animate().scaleX(1f).scaleY(1f).setDuration(200L).start()
                }
            }
            true
        }

        tvError.setOnClickListener {
            tvError.text = ""
            tvError.visibility = GONE
        }

    }

    private fun startLoggingIn(sharedPrefs: SharedPreferences, loginInfo: LoginInfo) {
        var answer: Answer? = null
        CoroutineScope(Dispatchers.Main).launch {
            networkScope.launch {
                answer = async { login(loginInfo) }.await()
            }.join()
            if (answer?.marker == "error") {
                showError(answer!!.message)
            } else {
                val token = Bundle()
                token.putString("token", answer?.message)
                val paymentsFragment = PaymentsFragment()
                paymentsFragment.arguments = token
                sharedPrefs.edit().putString("token", answer?.message).apply()
                parentFragmentManager.beginTransaction().replace(R.id.container, paymentsFragment)
                    .commit()
            }
        }
    }

    private fun login(loginInfo: LoginInfo) : Answer {
        var marker: String
        var response: String?
        try {
            with(App().getApi().login(loginInfo).execute()) {
                if (body()?.tokenBody?.token != null) {
                    response = body()?.tokenBody?.token
                    marker = "success"
                } else {
                    response = body()?.error?.message ?: "unknown error"
                    marker = "error"
                }
            }
        } catch (e: Exception) {
            response = e.message.toString()
            marker = "error"
        }
        return Answer(marker, response.toString())
    }

    private fun showError(error: String) {
        tvError.visibility = VISIBLE
        tvError.text = error
    }
}