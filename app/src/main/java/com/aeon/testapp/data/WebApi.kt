package com.aeon.testapp.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface WebApi {

    @Headers("app-key: 12345", "v: 1")
    @POST("login")
    fun login(
        @Query("login") login: String,
        @Query("password") password: String,
    ) : Call<String>

    @Headers("app-key: 12345", "v: 1")
    @GET("payments")
    fun getPayments(
        @Query("token") token: String
    ) : Call<List<Any>>
}