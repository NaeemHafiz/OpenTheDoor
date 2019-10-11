package com.example.openthedoor.repository

import android.annotation.SuppressLint
import android.app.Application
import com.example.openthedoor.data.remote.openthedoor.OpenTheDoorRetroFitClientInstance
import com.example.openthedoor.data.remote.openthedoor.UserDataSource
import com.example.openthedoor.data.remote.openthedoor.model.params.LoginParams
import com.example.openthedoor.data.remote.openthedoor.model.params.SendCoordinatesParams
import com.example.openthedoor.utils.ErrorUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UserRepository(var context: Application) {


    fun getApiService() = OpenTheDoorRetroFitClientInstance.getInstance(context)!!.getService()

    @SuppressLint("CheckResult")
    fun login(phone: String, password: String, callback: UserDataSource.LoginCallback) {
        val params = LoginParams()
        params.phone = phone
        params.password = password

        getApiService().login(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ callback.onLoginResponse(it.data) }
                , {
                    callback.onPayloadError(ErrorUtils.parseError(it))
                })
    }
    @SuppressLint("CheckResult")
    fun getChildren(driver_id: String, callback: UserDataSource.GetChildrenCallback) {

        getApiService().getChildren(driver_id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ callback.onGetChildrenResponse(it) }
                , {
                    callback.onPayloadError(ErrorUtils.parseError(it))
                })
    }

    @SuppressLint("CheckResult")
    fun sendCoordinates(
        userId: String,
        latitude: String,
        longitude: String,
        callback: UserDataSource.SendCoordinatesCallback
    ) {
        val params = SendCoordinatesParams()
        params.driver_id = userId
        params.latitude = latitude
        params.longitude = longitude

        getApiService().sendCoordinates(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ callback.onSendCoordinatesResponse(it.message) }
                , {
                    callback.onPayloadError(ErrorUtils.parseError(it))
                })
    }

    @SuppressLint("CheckResult")
    fun changeOnlineStatus(
        driverId: String,
        status: String,
        callback: UserDataSource.OnlineStatusChangedCallback
    ) {
        getApiService().changeOnlineStatus(driverId, status)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ callback.onOnlineStatusChangedResponse(it.message) }
                , {
                    callback.onPayloadError(ErrorUtils.parseError(it))
                })
    }
}
