package com.example.openthedoor.view.fragments.base

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.openthedoor.R
import com.example.openthedoor.data.sharedPreferences.AppPreferences
import com.example.openthedoor.utils.extensions.hideKeyboard
import com.example.openthedoor.view.activities.GlobalNavigationActivity
import com.example.openthedoor.view.activities.base.BaseActivity
import timber.log.Timber
import java.io.File

abstract class BaseFragment : Fragment() {

    abstract fun getLayoutId(): Int

    lateinit var dialog: ProgressDialog
    lateinit var appPreferences: AppPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!::dialog.isInitialized) {
            dialog = ProgressDialog(activity)
            dialog.setTitle(getString(R.string.title_please_wait))
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
            dialog.setCancelable(false)
        }

        if (!::appPreferences.isInitialized) {
            appPreferences = AppPreferences(context!!)
        }
    }

    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showToast(resId: Int) {
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show()
    }

    fun logError(message: String) = Timber.e(message)

    fun showProgressDialog(show: Boolean) {
        if (show) {
            if (!dialog.isShowing)
                dialog.apply {
                    show()
                }
        } else if (!show) {
            if (dialog.isShowing)
                dialog.dismiss()
        }
    }
    fun deleteFile(filePath: String) {
        val file = File(filePath)
        if (file.exists()) {
            val fileDeleted = file.delete()

            if (fileDeleted)
                logError("File deleted")
            else
                logError("File not deleted")

        } else
            logError("File not found!")
    }
    fun moveToGlobalNavigationActivity() {
        startActivity(GlobalNavigationActivity.Companion.getStartIntent(activity as BaseActivity))
        (activity as BaseActivity).finish()
    }
    override fun onPause() {
        super.onPause()
        if (view != null) {
            if (isRemoving)
                hideKeyboard(view!!)
        }
    }
}