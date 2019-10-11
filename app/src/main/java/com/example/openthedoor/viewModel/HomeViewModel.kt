package com.example.openthedoor.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.openthedoor.data.remote.base.ApiErrorResponse
import com.example.openthedoor.data.remote.openthedoor.UserDataSource
import com.example.openthedoor.repository.UserRepository
import com.example.openthedoor.utils.extensions.OneShotEvent

class HomeViewModel(context: Application) : BaseAndroidViewModel(context) {

    var userRepository: UserRepository = UserRepository(
        context
    )
    var updateOnlineStatusResponse: MutableLiveData<OneShotEvent<String>> = MutableLiveData()
    var getChildrenResponse: MutableLiveData<OneShotEvent<String>> = MutableLiveData()


    fun changeOnlineStatus(userId: String, status: String) {
        showProgressBar(true)
        userRepository.changeOnlineStatus(
            userId,
            status,
            object : UserDataSource.OnlineStatusChangedCallback {
                override fun onOnlineStatusChangedResponse(message: String) {
                    showProgressBar(false)
                    updateOnlineStatusResponse.value = OneShotEvent(message)
                }

                override fun onPayloadError(error: ApiErrorResponse) {
                    showProgressBar(false)
                    showSnackbarMessage(error.message)
                }
            })
    }




}


