package  com.example.openthedoor.data.remote.openthedoor

import com.example.openthedoor.data.remote.openthedoor.model.params.LoginParams
import com.example.openthedoor.data.remote.openthedoor.model.params.SendCoordinatesParams
import com.example.openthedoor.data.remote.openthedoor.model.response.GetChildResponse
import com.example.openthedoor.data.remote.openthedoor.model.response.LoginResponse
import com.example.openthedoor.data.remote.openthedoor.model.response.SendCoordinatesResponse
import com.example.openthedoor.data.remote.openthedoor.model.response.base.BaseResponse
import io.reactivex.Observable
import retrofit2.http.*

interface ApiService {

    @POST("login")
    fun login(@Body body: LoginParams): Observable<LoginResponse>

    @POST("send_coordinates")
    fun sendCoordinates(@Body body: SendCoordinatesParams): Observable<SendCoordinatesResponse>

    @FormUrlEncoded
    @PUT("is_online")
    fun changeOnlineStatus(
        @Field("driver_id") driverId: String,
        @Field("status") status: String
    ): Observable<BaseResponse>

    @GET("assigned_children")
    fun getChildren(@Query("driver_id") parent_id: String): Observable<GetChildResponse>
}
