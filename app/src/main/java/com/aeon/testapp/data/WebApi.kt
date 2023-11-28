package com.aeon.testapp.data

import com.aeon.testapp.data.models.LoginInfo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface WebApi {

    @Headers("app-key: 12345", "v: 1")
    @POST("login")
    fun login(
        @Body loginInfo: LoginInfo
    ) : Call<String>

    @Headers("app-key: 12345", "v: 1", "token: ")
    @GET("payments")
    fun getPayments() : Call<List<Any>>
}