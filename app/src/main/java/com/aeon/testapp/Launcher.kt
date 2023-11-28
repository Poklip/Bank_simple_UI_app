package com.aeon.testapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class Launcher : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("KVS_DEBUG", App().getApi().login(login = "demo", password = "12345").execute().isSuccessful.toString())
    }
}