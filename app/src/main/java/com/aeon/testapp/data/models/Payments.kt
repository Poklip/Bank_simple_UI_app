package com.aeon.testapp.data.models

import com.google.gson.annotations.SerializedName

data class Payments(
    @SerializedName("success")
    val isSuccessful: String,
    @SerializedName("response")
    val paymentsList: List<Payment>
)
