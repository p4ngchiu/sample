package com.hanmirae.emoney.data.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

object SharedPreferencesManger {
    fun of(activity: AppCompatActivity): SharedPreferences = activity.getSharedPreferences(activity.javaClass.name, Context.MODE_PRIVATE)

    fun of(fragment: Fragment): SharedPreferences? = fragment.context?.getSharedPreferences(fragment.javaClass.name, Context.MODE_PRIVATE)

    fun of(context: Context, name: String): SharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)
}