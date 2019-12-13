package com.example.cloneapp.utils

import android.app.Activity
import android.content.Context
import com.example.cloneapp.presentasion.dialog.LoadingDialog


/**
 * Created by cantran.
 */
object ProgressDialogUtils {
    private var mLoadingDialog: LoadingDialog? = null


    fun showProgressDialog(context: Context?) {
        if (context is Activity) {
            if (context.isFinishing) {
                return
            }
        }

        if (context == null) return

        try {
            if (mLoadingDialog != null) {
                if (!mLoadingDialog!!.isShowing) {
                    mLoadingDialog!!.show()
                }
            } else {
                mLoadingDialog = LoadingDialog(context)
                mLoadingDialog!!.show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            mLoadingDialog = null
            showProgressDialog(context)
        }

    }

    fun dismissProgressDialog() {
        try {
            if (mLoadingDialog != null && mLoadingDialog!!.isShowing) {
                mLoadingDialog!!.dismiss()
                mLoadingDialog = null
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}
