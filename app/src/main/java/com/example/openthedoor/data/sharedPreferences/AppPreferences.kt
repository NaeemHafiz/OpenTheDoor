package com.example.openthedoor.data.sharedPreferences

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.example.openthedoor.data.remote.openthedoor.OpenTheDoorRetroFitClientInstance
import com.example.openthedoor.data.remote.openthedoor.model.data.LoginData
import com.example.openthedoor.view.activities.LandingActivity
import com.example.openthedoor.view.activities.base.BaseActivity


class AppPreferences(context: Context) {
    var context: Context? = context
    var pref: SharedPreferences

    init {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun isLoggedIn(): Boolean = getAuthenticationToken().isNotEmpty()

    fun setAuthenticationToken(token: String) {
        with(pref.edit()) {
            putString(TOKEN, token)
            apply()
        }
        OpenTheDoorRetroFitClientInstance.getInstance(context!!)!!.initRetrofit()
    }

    fun getAuthenticationToken(): String {
        return pref.getString(TOKEN, "")!!
    }

    private fun clearAppPreferences() {
        with(pref.edit()) {
            clear().apply()
        }
    }

    fun logout() {
        clearAppPreferences()
    }

    fun redirectToLogin(context: Context, message: String) {
        val intent = Intent(context, LandingActivity::class.java)
//        intent.putExtra(LandingActivity.SKIP_SPLASH, true)
        intent.putExtra(LandingActivity.START_UP_MESSAGE, message)
        intent.flags = (Intent.FLAG_ACTIVITY_CLEAR_TASK
                or Intent.FLAG_ACTIVITY_CLEAR_TOP
                or Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
        (context as BaseActivity).finishAffinity()
    }

    fun setUser(loginData: LoginData) {
        setUserId(loginData.user.id)
        setUserFirstName(loginData.user.fname)
        setUserLastName(loginData.user.lname)
        setUserPhone(loginData.user.phone)
        setUserEmail(loginData.user.email)
        setUserType(loginData.user.type)
        setUserPhoneVerified(loginData.user.phone_verified_at)
        setUserEmailVerified(loginData.user.email_verified_at)
        setUserCnic(loginData.user.cnic)
        setUserAddress(loginData.user.address)
    }

    fun getUser(): LoginData {
        val loginData = LoginData()

        loginData.user.fname = getUserFirstName().toString()
        loginData.user.lname = getUserLastName().toString()
        loginData.user.phone = getUserPhone().toString()
        loginData.user.email = getUserEmail().toString()
        loginData.user.address = getUserAddress().toString()
        loginData.user.cnic = getUserCnic().toString()
        loginData.user.type = getUserType().toString()
        loginData.user.id = getUserId()
        loginData.user.email_verified_at = getUserEmailVerified().toString()
        loginData.user.phone_verified_at = getUserPhoneVerified().toString()

        return loginData
    }

    fun setFcmToken(token: String) {
        with(pref.edit()) {
            putString(KEY_FCM_TOKEN, token)
            apply()
        }
    }

    fun getFcmToken(): String {
        return pref.getString(KEY_FCM_TOKEN, "")!!
    }

    private fun setUserType(user_type: String?) {
        with(pref.edit()) {
            putString(KEY_USER_TYPE, user_type)
            apply()
        }
    }

    private fun getUserType(): String? {
        return pref.getString(KEY_USER_TYPE, "")
    }

    private fun setUserProfileImage(profile_image: String?) {
        with(pref.edit()) {
            putString(KEY_USER_PROFILE_IMAGE, profile_image)
            apply()
        }
    }

    private fun getUserProfileImage(): String? {
        return pref.getString(KEY_USER_PROFILE_IMAGE, "")
    }


    fun setUserEmail(email: String) {
        with(pref.edit()) {
            putString(KEY_USER_EMAIL, email)
            apply()
        }
    }

    private fun getUserEmail(): String? {
        return pref.getString(KEY_USER_EMAIL, "")
    }

    private fun setUserName(name: String) {
        with(pref.edit()) {
            putString(KEY_USER_NAME, name)
            apply()
        }
    }

    private fun getUserName(): String? {
        return pref.getString(KEY_USER_NAME, "")
    }

    fun setUserFirstName(name: String) {
        with(pref.edit()) {
            putString(KEY_USER_FIRST_NAME, name)
            apply()
        }
    }

    private fun getUserFirstName(): String? {
        return pref.getString(KEY_USER_FIRST_NAME, "")
    }

    fun setUserLastName(name: String) {
        with(pref.edit()) {
            putString(KEY_USER_LAST_NAME, name)
            apply()
        }
    }

    private fun getUserLastName(): String? {
        return pref.getString(KEY_USER_LAST_NAME, "")
    }

    fun setUserPhone(phone: String) {
        with(pref.edit()) {
            putString(KEY_USER_PHONE, phone)
            apply()
        }
    }

    private fun getUserPhone(): String? {
        return pref.getString(KEY_USER_PHONE, "")
    }

    private fun setUserId(id: Int) {
        with(pref.edit()) {
            putInt(KEY_USER_ID, id)
            apply()
        }
    }

    private fun getUserId(): Int {
        return pref.getInt(KEY_USER_ID, -1)
    }

    fun setLocale(locale: String) {
        with(pref.edit()) {
            putString(KEY_LOCALE, locale)
            apply()
        }
    }

    private fun setUserAddress(address: String) {
        with(pref.edit()) {
            putString(KEY_USER_PHONE, address)
            apply()
        }

    }

    private fun getUserAddress(): String? {
        return pref.getString(KEY_USER_ADDRESS, "")
    }

    private fun getUserCnic(): String? {
        return pref.getString(KEY_USER_CNIC, "")
    }

    private fun getUserPhoneVerified(): String? {
        return pref.getString(KEY_USER_VERIFIED_PHONE, "")
    }

    private fun getUserEmailVerified(): String? {
        return pref.getString(KEY_USER_VERIFIED_EMAIL, "")
    }

    private fun setUserCnic(cnic: String) {
        with(pref.edit()) {
            putString(KEY_USER_PHONE, cnic)
            apply()
        }
    }

    private fun setUserEmailVerified(email_verified_at: String?) {
        with(pref.edit()) {
            putString(KEY_USER_PHONE, email_verified_at)
            apply()
        }
    }

    private fun setUserPhoneVerified(phone_verified_at: String?) {
        with(pref.edit()) {
            putString(KEY_USER_PHONE, phone_verified_at)
            apply()
        }
    }

    fun getLocale(): String {
        return pref.getString(KEY_LOCALE, "en")!!
    }

    fun setNotifications(flag: Boolean) {
        with(pref.edit()) {
            putBoolean(KEY_NOTIFICATIONS, flag)
            apply()
        }
    }

    fun getNotifications(): Boolean {
        return pref.getBoolean(KEY_NOTIFICATIONS, true)
    }

    companion object {

        const val PREF_NAME: String = "app_pref"
        const val TOKEN: String = "authentication_token"
        const val KEY_FCM_TOKEN = "key_fcm_token"

        const val KEY_USER_ID: String = "user_id"
        const val KEY_USER_NAME = "name"
        const val KEY_USER_FIRST_NAME = "firstName"
        const val KEY_USER_LAST_NAME = "lastName"
        const val KEY_USER_EMAIL = "email"
        const val KEY_USER_PROFILE_IMAGE = "profile_pic"
        const val KEY_USER_PHONE = "phone"
        const val KEY_USER_TYPE = "user_type"
        const val KEY_USER_ADDRESS = "user_address"
        const val KEY_USER_CNIC = "user_cnic"
        const val KEY_USER_VERIFIED_EMAIL = "user_email_verified"
        const val KEY_USER_VERIFIED_PHONE = "user_phone_verified"

        const val KEY_LOCALE = "locale"
        const val KEY_NOTIFICATIONS = "notifications"
    }
}