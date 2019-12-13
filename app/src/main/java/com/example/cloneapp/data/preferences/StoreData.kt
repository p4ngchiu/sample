package com.example.cloneapp.data.preferences

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hanmirae.emoney.data.preferences.SharedPreferencesManger


class StoreData constructor(context: Context) {
    private val gson by lazy { Gson() }
    private val pref = SharedPreferencesManger.of(context, PREF_NAME)

    var accessToken: String?
        get() = pref.getString(KEY_ACCESS_TOKEN, null)
        set(value) = pref.edit {
            putString(KEY_ACCESS_TOKEN, value)
        }

    var statusAutoPrinterConfig: Boolean
        get() = pref.getBoolean(AUTO_PRINTER_CONFIG, false)
        set(value) = pref.edit { putBoolean(AUTO_PRINTER_CONFIG, value) }

    var currentDevice: String?
        get() = pref.getString(CURRENT_DEVICE, null)
        set(value) = pref.edit {
            putString(CURRENT_DEVICE, value)
        }

    fun saveListDevicesInfo(listDeviceInfo: ArrayList<String>) {
        val json = gson.toJson(listDeviceInfo)
        pref.edit {
            putString(LIST_DEVICE_INFO, json)
        }
    }

    fun getistDevicesInfo(): ArrayList<String>? {
        val json = pref.getString(LIST_DEVICE_INFO, "")
        val type = object : TypeToken<List<String>>() {
        }.type
        return gson.fromJson(json, type)
    }


    var deviceToken: String?
        get() = pref.getString(KEY_DEVICE_TOKEN, null)
        set(value) = pref.edit {
            putString(KEY_DEVICE_TOKEN, value)
        }

    var language: String?
        get() = pref.getString(KEY_LANGUAGE, LANGUAGE_DEFAULT)
        set(value) = pref.edit {
            putString(KEY_LANGUAGE, value)
        }

    var firstLogin: Boolean
        get() = pref.getBoolean(KEY_FIRST_LOGIN, false)
        set(value) = pref.edit { putBoolean(KEY_FIRST_LOGIN, value) }

    var loginWithBiometricPrompt: Boolean
        get() = pref.getBoolean(KEY_LOGIN_WITH_BIOMETRIC, false)
        set(value) = pref.edit { putBoolean(KEY_LOGIN_WITH_BIOMETRIC, value) }

    var eInfo: String?
        get() = pref.getString(KEY_E_INFO, null)
        set(value) = pref.edit {
            putString(KEY_E_INFO, value)
        }

    fun clearLogout() {
        pref.edit().remove(KEY_ACCESS_TOKEN).apply()
        pref.edit().remove(KEY_DEVICE_TOKEN).apply()
        pref.edit().remove(KEY_USER_INFO).apply()
        pref.edit().remove(KEY_FIRST_LOGIN).apply()
        pref.edit().remove(KEY_LOGIN_WITH_BIOMETRIC).apply()
        pref.edit().remove(currentDevice).apply()
    }

    var currentcy: Int
        get() = pref.getInt(KEY_CURRENTCY, 2)
        set(value) = pref.edit {
            putInt(KEY_CURRENTCY, value)
        }

    companion object {

        private const val PREF_NAME = "UserPrefs"
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val CURRENT_DEVICE = "current_device"
        private const val LIST_DEVICE_INFO = "list_device_info"
        private const val LIST_SCREEN_SETTING = "list_screen_setting"
        private const val KEY_DEVICE_TOKEN = "device_token"
        private const val KEY_USER_INFO = "user_info"
        private const val KEY_LANGUAGE = "key_language"
        private const val LANGUAGE_DEFAULT = "en"
        private const val KEY_FIRST_LOGIN = "key_first_login"
        private const val AUTO_PRINTER_CONFIG = "auto_printer_config"
        private const val KEY_E_INFO = "key_e_info"
        private const val KEY_LOGIN_WITH_BIOMETRIC = "login_with_biometric"
        private const val KEY_CURRENTCY = "key_currentcy"
        private const val KEY_APP_CONFIG = "key_appconfig"
        const val DB_NAME = "emoney"
    }
}