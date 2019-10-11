package  com.example.openthedoor.data.remote.openthedoor.model.response

import com.example.openthedoor.data.remote.openthedoor.model.data.LoginData
import com.example.openthedoor.data.remote.openthedoor.model.response.base.BaseResponse
import java.io.Serializable

class LoginResponse(
    val data: LoginData
) : BaseResponse(), Serializable