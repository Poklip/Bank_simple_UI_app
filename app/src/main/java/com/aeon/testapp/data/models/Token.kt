package com.aeon.testapp.data.models

import com.google.gson.annotations.SerializedName

data class ResponseToken(
    @SerializedName("success") val isSuccessful: String,
    @SerializedName("response") val tokenBody: Token,
    @SerializedName("error") val error: RequestError,
)

data class Token(
    @SerializedName("token") val token: String,
)
