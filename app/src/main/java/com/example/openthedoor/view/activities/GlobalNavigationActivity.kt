package com.example.openthedoor.view.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.openthedoor.R
import com.example.openthedoor.view.activities.base.BaseActivity
import com.example.openthedoor.viewModel.HomeViewModel
import kotlinx.android.synthetic.main.activity_global_navigation.*


class GlobalNavigationActivity : BaseActivity() {
    lateinit var navController: NavController

    lateinit var homeViewModel: HomeViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_global_navigation)
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        initNavigation()
    }


    @SuppressLint("SetTextI18n")
    private fun initNavigation() {
        ivDrawer.setOnClickListener { drawer.openDrawer(GravityCompat.START, true) }

        navController =
            Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(nav_view, navController)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            rlToolbar.visibility = View.VISIBLE
            tvTitle.text = destination.label
        }

        nav_view.menu.findItem(R.id.logout).setOnMenuItemClickListener {
            logout()
            true
        }
        val tvFullName = nav_view.getHeaderView(0).findViewById<TextView>(R.id.tvFullName)
        val tvDriverNumber = nav_view.getHeaderView(0).findViewById<TextView>(R.id.tvDriverNumber)
        tvFullName.text =
            "${appPreferences.getUser().user.fname} ${appPreferences.getUser().user.lname}"
        tvDriverNumber.text = appPreferences.getUser().user.phone
        val swOnline = nav_view.getHeaderView(0).findViewById<SwitchCompat>(R.id.swStatus)
        swOnline.isChecked = false

        swOnline.setOnCheckedChangeListener { buttonView, isChecked ->
            homeViewModel.changeOnlineStatus(
                appPreferences.getUser().user.id.toString(),
                if (isChecked) "1" else "0"
            )
        }
    }

    private var flagDoubleBackToExitPressedOnce = false

    override fun onBackPressed() {
        when {
            drawer.isDrawerOpen(GravityCompat.START) -> drawer.closeDrawers()
            navController.currentDestination!!.label!!.toString() != "Home" -> super.onBackPressed()
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

    object Companion {
        @JvmStatic
        fun getStartIntent(context: Context) = Intent(context, GlobalNavigationActivity::class.java)
    }
}