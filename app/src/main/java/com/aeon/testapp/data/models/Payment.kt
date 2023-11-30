package com.aeon.testapp.data.models

import com.google.gson.annotations.SerializedName

data class Payment(
    @SerializedName("id")
    val paymentId: Int,
    @SerializedName("title")
    val paymentTitle: String,
    @SerializedName("amount")
    val paymentAmount: Any?,
    @SerializedName("created")
    val paymentCreated: Long?
)