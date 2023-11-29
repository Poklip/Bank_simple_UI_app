package com.aeon.testapp.data.models

import com.google.gson.annotations.SerializedName

data class RequestError(
    @SerializedName("error_code") val code: Int,
    @SerializedName("error_msg") val message: String,
)
