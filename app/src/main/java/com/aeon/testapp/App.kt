package com.aeon.testapp

import android.app.Application
import com.aeon.testapp.data.WebApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {

    var token: String? = null

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    fun getApi() : WebApi {
        return retrofit.create(WebApi::class.java)
    }

}