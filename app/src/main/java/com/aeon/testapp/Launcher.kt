package com.aeon.testapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aeon.testapp.ui.LoginFragment
import com.google.android.material.R

class Launcher : AppCompatActivity() {
    var token = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction().replace(R.id.container, LoginFragment())
            .commit()
    }
}