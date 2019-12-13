package com.example.cloneapp.data.interceptor

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.telephony.TelephonyManager
import com.example.cloneapp.BuildConfig
import com.example.cloneapp.R
import com.example.cloneapp.common.App
import java.util.*


object DeviceInfoHelper {
    fun getEinfo(): String =
        App.get().resources.getString(R.string.android).plus(";").plus(Build.VERSION.RELEASE).plus(
            ";"
        ).plus(Build.MANUFACTURER).plus(" ").plus(Build.MODEL).plus(";").plus(
            CLIENT_TYPE_END_USER
        ).plus(";").plus(getImeiDevice() ?: UUID.randomUUID().toString()).plus(";").plus(BuildConfig.VERSION_NAME)

    @SuppressLint("MissingPermission")
    fun getImeiDevice(): String? {
        return try {
            var telephonyManager =
                App.get().getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            if (Build.VERSION.SDK_INT > 26)
                telephonyManager.imei
            else telephonyManager.deviceId
        }catch (e: Exception){
            e.printStackTrace()
            null
        }
    }

    const val CLIENT_TYPE_END_USER = 1
    const val CLIENT_TYPE_AGENT = "2"

}