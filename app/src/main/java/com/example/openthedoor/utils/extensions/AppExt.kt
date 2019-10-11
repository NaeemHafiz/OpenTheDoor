package com.example.openthedoor.utils.extensions

import android.Manifest
import android.content.Context
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.openthedoor.view.activities.base.BaseActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.text.SimpleDateFormat
import java.util.*

fun isEmailValid(target: CharSequence): Boolean {
    return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
}

fun isPhoneValid(phoneNumber: String): Boolean {
    return (!TextUtils.isEmpty(phoneNumber.trim { it <= ' ' })
            && Patterns.PHONE.matcher(phoneNumber.trim { it <= ' ' }).matches()
            && phoneNumber.length > 10)
}

fun loadProfileImage(imageUrl: String, imageView: ImageView) {
    Glide.with(imageView.context).load(imageUrl)
        .apply(RequestOptions().centerCrop().circleCrop().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
        .into(imageView)
}

fun isLocationEnabled(activity: BaseActivity): Boolean {
    var isGranted = false
    var isGrantedCoarse = false
    Dexter.withActivity((activity))
        .withPermissions(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        .withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                for (response: PermissionGrantedResponse in report!!.grantedPermissionResponses) {
                    if (response.permissionName == Manifest.permission.ACCESS_FINE_LOCATION)
                        isGranted = true
                    else if (response.permissionName == Manifest.permission.ACCESS_COARSE_LOCATION)
                        isGrantedCoarse = true
                }
                for (response: PermissionDeniedResponse in report.deniedPermissionResponses) {
                    if (response.permissionName == Manifest.permission.ACCESS_FINE_LOCATION)
                        isGranted = false
                    else if (response.permissionName == Manifest.permission.ACCESS_COARSE_LOCATION)
                        isGrantedCoarse = false
                }
            }

            override fun onPermissionRationaleShouldBeShown(
                permissions: MutableList<PermissionRequest>?,
                token: PermissionToken?
            ) {
            }

        }).check()

    return isGranted && isGrantedCoarse
}



fun hideKeyboard(view: View) {
    try {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    } catch (e: NullPointerException) {
        e.printStackTrace()
    }
}

fun showKeyboard(view: View) {
    try {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, 0)
    } catch (e: NullPointerException) {
        e.printStackTrace()
    }

}


fun getFormatedDateTime(
    dateStr: String,
    strReadFormat: String = "HH:mm:ss",
    strWriteFormat: String = "hh:mm a"
): String {

    var formattedDate = dateStr

    val readFormat = SimpleDateFormat(strReadFormat, Locale.getDefault())
    val writeFormat = SimpleDateFormat(strWriteFormat, Locale.getDefault())

    var date: Date? = null

    try {
        date = readFormat.parse(dateStr)
    } catch (e: Exception) {
    }

    if (date != null) {
        formattedDate = writeFormat.format(date)
    }

    return formattedDate
}

fun getRequestCode(): Int {
    val rnd = Random()
    return 100 + rnd.nextInt(900000)
}