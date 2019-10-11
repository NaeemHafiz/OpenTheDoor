package com.example.openthedoor.utils

import com.google.gson.JsonParser
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import com.example.openthedoor.data.remote.base.ApiErrorResponse
import org.json.JSONObject

object ErrorUtils {

    fun parseError(json: String): ApiErrorResponse {
        return try {
            val json = JSONObject(json)
            val error = ApiErrorResponse(
                json.optInt("code", 0),
                json.optString("message", ""),
                json.optString("name", "")
            )
            error
        } catch (ex: Exception) {
            ApiErrorResponse(0, "", "")
        }
    }

    fun parseError(t: Throwable): ApiErrorResponse {
        var message = ""
        if (t is HttpException) {
            val errorJsonString = t.response()
                .errorBody()?.string()
            if (JsonParser().parse(errorJsonString)
                    .asJsonObject["Message"] != null
            ) {
                message = JsonParser().parse(errorJsonString)
                    .asJsonObject["Message"]
                    .asString
            }
        } else {
            message = t.message ?: message
        }

        return try {
            ApiErrorResponse(0, message, "")
        } catch (ex: Exception) {
            ex.printStackTrace()
            ApiErrorResponse(0, message, "")
        }
    }
}