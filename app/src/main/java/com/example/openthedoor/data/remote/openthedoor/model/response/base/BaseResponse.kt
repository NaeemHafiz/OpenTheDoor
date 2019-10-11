package com.example.openthedoor.data.remote.openthedoor.model.response.base

import java.io.Serializable

open class BaseResponse: Serializable {

    var status: Int = -1
    var message: String = ""
}