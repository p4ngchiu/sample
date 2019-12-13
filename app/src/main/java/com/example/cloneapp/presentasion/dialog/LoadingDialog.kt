package com.example.cloneapp.presentasion.dialog

import android.app.Dialog
import android.content.Context
import com.example.cloneapp.R


/**
 * Created by cantran.
 */
class LoadingDialog(context: Context) : Dialog(context, R.style.Theme_CustomProgressDialog) {
    init {
        setContentView(R.layout.dialog_custom_progress)
        setCancelable(false)
    }
}