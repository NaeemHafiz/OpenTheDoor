package com.example.openthedoor.view.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.openthedoor.R


class AlertMessageDialog : DialogFragment() {

    var message: String = ""
    var btnText: String = "Ok"

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            arguments?.let {
                message = it.getString(MESSAGE, "")
            }
            val builder = AlertDialog.Builder(it)

            builder.setTitle(R.string.label_alert)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok) { dialog, id ->
                    dialog.dismiss()
                }
                .setCancelable(false)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    companion object {
        val TAG: String = this::class.java.simpleName
        val MESSAGE: String = "message"
        fun newInstance(message: String): AlertMessageDialog {
            val dialogAlert = AlertMessageDialog()
            val bundle = Bundle()
            bundle.putString(MESSAGE, message)
            dialogAlert.arguments = bundle
            return dialogAlert
        }
    }


}

