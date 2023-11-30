package com.aeon.testapp

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aeon.testapp.ui.LoginFragment
import com.aeon.testapp.ui.PaymentsFragment

class Launcher : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPrefs = getSharedPreferences("SP", Context.MODE_PRIVATE)
        val tokenValue = sharedPrefs.getString("token", "")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.launcher)
        val token = Bundle()
        token.putString("token", tokenValue)
        val paymentsFragment = PaymentsFragment()
        paymentsFragment.arguments = token

        if (tokenValue != "") {
            supportFragmentManager.beginTransaction().replace(R.id.container, paymentsFragment)
                .commit()
        } else {
            supportFragmentManager.beginTransaction().replace(R.id.container, LoginFragment())
                .commit()
        }


    }
}