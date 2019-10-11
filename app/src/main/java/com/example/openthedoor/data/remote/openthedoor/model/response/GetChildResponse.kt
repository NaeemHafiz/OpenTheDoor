package com.example.openthedoor.data.remote.openthedoor.model.response

import com.example.openthedoor.data.remote.openthedoor.model.data.GetChildrenData
import com.example.openthedoor.data.remote.openthedoor.model.response.base.BaseResponse
import java.io.Serializable

class GetChildResponse(
    val data:List<List<GetChildrenData>>
) : BaseResponse(), Serializable