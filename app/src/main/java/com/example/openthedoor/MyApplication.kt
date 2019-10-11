package com.example.openthedoor

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.example.openthedoor.data.sharedPreferences.AppPreferences
import com.example.openthedoor.utils.application.MyContextWrapper
import java.util.*

class MyApplication : MultiDexApplication() {

    override fun attachBaseContext(newBase: Context?) {
        val newLocale = Locale(AppPreferences(newBase!!).getLocale())
        val context = MyContextWrapper.wrapApplication(newBase, newLocale)
        super.attachBaseContext(context)
        MultiDex.install(this)
    }

    init {
        instance = this
    }

    companion object {
        @JvmStatic
        val TAG: String = MyApplication::class.java.simpleName

        var instance = MyApplication()
    }
}