package com.aeon.testapp.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.aeon.testapp.R
import com.aeon.testapp.data.models.LoginInfo

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val loginInfo: LoginInfo? = null
    private val etLogin: EditText by lazy { requireActivity().findViewById(R.id.etLogin) }
    private val etPassword: EditText by lazy { requireActivity().findViewById(R.id.etPassword) }
    private val btnLogin: Button by lazy { requireActivity().findViewById(R.id.btnLogin) }
    private val tvError: TextView by lazy { requireActivity().findViewById(R.id.tvError) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}