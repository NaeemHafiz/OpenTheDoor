package com.example.openthedoor.view.activities

import android.os.Bundle
import android.os.Handler
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.openthedoor.R
import com.example.openthedoor.view.activities.base.BaseActivity

class LandingActivity : BaseActivity() {

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        initNavigation()

        val startMessage = intent.getStringExtra(START_UP_MESSAGE)

        if (!startMessage.isNullOrEmpty()) showAlertDialog(startMessage)

        if (appPreferences.isLoggedIn()) moveToGlobalNavigationActivity()
    }

    private fun initNavigation() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
    }

    private var flagDoubleBackToExitPressedOnce = false

    override fun onBackPressed() {
        when {
            navController.currentDestination!!.label!!.toString() != "SplashFragment" -> super.onBackPressed()
            else -> {
                if (flagDoubleBackToExitPressedOnce) {
                    super.onBackPressed()
                    return
                }

                this.flagDoubleBackToExitPressedOnce = true
                showToast(R.string.warn_press_back_to_exit)

                Handler().postDelayed({ flagDoubleBackToExitPressedOnce = false }, 2000)
            }
        }
    }

    companion object {
        const val START_UP_MESSAGE: String = "start_up_message"
    }
}
