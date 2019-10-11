package com.example.openthedoor.data.remote.openthedoor.model.response

import com.example.openthedoor.data.remote.openthedoor.model.response.base.BaseResponse
import java.io.Serializable

class SendCoordinatesResponse(
    val data: Data
) : BaseResponse(), Serializable{

    inner class Data{

    }
}

