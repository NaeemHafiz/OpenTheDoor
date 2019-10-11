package com.example.openthedoor.view.activities.base

import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.openthedoor.R
import com.example.openthedoor.data.sharedPreferences.AppPreferences
import com.example.openthedoor.view.activities.GlobalNavigationActivity
import com.example.openthedoor.view.dialogs.AlertMessageDialog

abstract class BaseActivity : AppCompatActivity() {

    lateinit var appPreferences: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!::appPreferences.isInitialized) appPreferences = AppPreferences(this)
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun showToast(resId: Int) {
        Toast.makeText(this, resId, Toast.LENGTH_LONG).show()
    }

    fun showAlertDialog(msg: String) {
        AlertMessageDialog.newInstance(msg).show(supportFragmentManager, AlertMessageDialog.TAG)
    }

    fun showAlertDialog(title: String, message: String, callback: DialogInterface.OnClickListener) {

        val dialog: AlertDialog.Builder = AlertDialog.Builder(this)
        dialog.setTitle(title)
        dialog.setMessage(message)
        dialog.setPositiveButton(getString(R.string.yes), callback)
        dialog.setNegativeButton(getString(R.string.cancel)) { dialogInterface: DialogInterface, _: Int ->
            dialogInterface.dismiss()
        }
        dialog.setCancelable(false)
        dialog.show()

    }

//    fun showNotification(payload: String, title: String = getString(R.string.app_name)) {
//
//        val notificationPayload = Gson().fromJson(payload, Notification::class.java)
//        val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val channel = NotificationChannel(
//                "YOUR_CHANNEL_ID",
//                "YOUR_CHANNEL_NAME",
//                NotificationManager.IMPORTANCE_DEFAULT
//            )
//            channel.description = "YOUR_NOTIFICATION_CHANNEL_DISCRIPTION"
//            mNotificationManager.createNotificationChannel(channel)
//        }
//        val mBuilder = NotificationCompat.Builder(applicationContext, "YOUR_CHANNEL_ID")
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            mBuilder.setSmallIcon(R.mipmap.ic_swap_launcher_circle);
//            mBuilder.setColor(ContextCompat.getColor(this, R.color.colorLoginBlack))
//        } else {
//            mBuilder.setSmallIcon(R.mipmap.ic_swap_launcher_circle);
//        }
//            .setContentTitle(title) // title for notification
//            .setContentText(notificationPayload.message)// message for notification
//            .setAutoCancel(true) // clear notification after click
//        val intent = Intent(applicationContext, GlobalNavigationActivity::class.java)
//        val pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
//        mBuilder.setContentIntent(pi)
//
//        mNotificationManager.notify(getRequestCode(), mBuilder.build())
//    }

    fun moveToGlobalNavigationActivity() {
        startActivity(GlobalNavigationActivity.Companion.getStartIntent(this))
        finish()
    }

    fun logout() {
        val dialog: AlertDialog.Builder = AlertDialog.Builder(this)
        dialog.setTitle("Logout")
        dialog.setMessage("Are you sure ?")
        dialog.setPositiveButton(getString(R.string.yes)) { _: DialogInterface, _: Int ->
            appPreferences.logout()
            appPreferences.redirectToLogin(this, getString(R.string.message_logout))
        }
        dialog.setNegativeButton(getString(R.string.cancel)) { dialogInterface: DialogInterface, _: Int ->
            dialogInterface.dismiss()
        }
        dialog.setCancelable(false)
        dialog.show()
    }


}