package com.example.cloneapp.presentasion.base

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cloneapp.presentasion.screen.login.viewmodel.LoginViewModel
import com.example.cloneapp.presentasion.screen.onboarding.viewmodel.OnBoadingViewModel
import com.example.cloneapp.data.base.getInstance
import com.example.cloneapp.data.repository.run.AuthReposImpl

class ViewModelFactory(
    context: Context,
    private val authRemoteDataSource: AuthReposImpl
) : ViewModelProvider.NewInstanceFactory() {
    private val application = when (context) {
        is Activity -> context.application
        is Fragment -> context.activity!!.application
        else -> throw IllegalStateException("unknown apllication: $context")
    }
    private var activity: Activity = when (context) {
        is Activity -> context
        is Fragment -> context.activity!!
        else -> throw IllegalStateException("unknown activity: $context")
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        with(modelClass) {
            when {
                isAssignableFrom(OnBoadingViewModel::class.java) -> {
                    OnBoadingViewModel(application)
                }
                isAssignableFrom(LoginViewModel::class.java) -> {
                    LoginViewModel(application, authRemoteDataSource)
                }
                else -> throw IllegalStateException("unknown view model: $modelClass")
            }
        } as T

    companion object {
        fun getInstance(activity: Context): ViewModelFactory = ViewModelFactory(
            activity,
            AuthReposImpl.getInstance()
        )
    }
}