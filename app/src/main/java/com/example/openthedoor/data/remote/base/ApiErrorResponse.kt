package com.example.openthedoor.data.remote.base

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ApiErrorResponse (
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("name")
    val name: String
): Serializable
