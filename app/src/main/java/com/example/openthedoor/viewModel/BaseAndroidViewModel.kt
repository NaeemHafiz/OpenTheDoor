package com.example.openthedoor.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.openthedoor.utils.extensions.ErrorResponse
import com.example.openthedoor.utils.extensions.OneShotEvent

open class BaseAndroidViewModel(context: Application) : AndroidViewModel(context) {

    init {

    }

    val snackbarMessage = MutableLiveData<OneShotEvent<String>>()
    val progressBar = MutableLiveData<OneShotEvent<Boolean>>()
    val validationResponse = MutableLiveData<OneShotEvent<ErrorResponse>>()

    protected fun getContext(): Context {
        return getApplication<Application>().applicationContext
    }

    protected fun getString(resId: Int): String {
        return getContext().getString(resId)
    }

    protected fun handleErrorType(errorType: Int, errorMessage: String) {
        val error = ErrorResponse(errorMessage, errorType)
        this.validationResponse.value = OneShotEvent(error)
    }

    protected fun showSnackbarMessage(message: String) {
        snackbarMessage.value = OneShotEvent(message)
    }

    protected fun showProgressBar(show: Boolean) {
        progressBar.value = OneShotEvent(show)
    }
}