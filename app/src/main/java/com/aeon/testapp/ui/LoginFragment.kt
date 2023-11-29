package com.aeon.testapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.aeon.testapp.App
import com.aeon.testapp.R
import com.aeon.testapp.data.models.LoginInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val loginInfo: LoginInfo? = null
    private val etLogin: EditText by lazy { requireActivity().findViewById(R.id.etLogin) }
    private val etPassword: EditText by lazy { requireActivity().findViewById(R.id.etPassword) }
    private val tvError: TextView by lazy { requireActivity().findViewById(R.id.tvError) }
    private val btnLogin: Button by lazy { requireActivity().findViewById(R.id.btnLogin) }
    private val networkScope = CoroutineScope(Dispatchers.IO)
    private val errorScope = CoroutineScope(Dispatchers.IO)

    data class Answer(val marker: String, val message: String)

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tempLogin = etLogin.text.toString()
        val tempPassword = etPassword.text.toString()


        btnLogin.setOnTouchListener { _, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    btnLogin.animate().scaleX(0.2f).scaleY(0.2f).setDuration(200L).start()
                    startLoggingIn()
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

    private fun startLoggingIn() {
        var answer: Answer? = null
        GlobalScope.launch(Dispatchers.Main) {
            networkScope.launch {
                answer = async { login(LoginInfo("demo", "12345")) }.await()
            }.join()
            if (answer?.marker == "error") {
                showError(answer!!.message)
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
        Log.d("KVS_DEBUG", response.toString())
        return Answer(marker, response.toString())
    }

    private fun showError(error: String) {
        tvError.visibility = VISIBLE
        tvError.text = error
    }
}