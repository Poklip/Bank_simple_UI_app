package com.aeon.testapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aeon.testapp.ui.LoginFragment

class Launcher : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.launcher)

        supportFragmentManager.beginTransaction().replace(R.id.container, LoginFragment())
            .commit()

    }
}