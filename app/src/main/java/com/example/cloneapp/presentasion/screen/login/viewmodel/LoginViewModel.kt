package com.example.cloneapp.presentasion.screen.login.viewmodel

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.example.cloneapp.R
import com.example.cloneapp.data.repository.run.AuthReposImpl
import com.example.cloneapp.data.request.LoginRequest
import com.example.cloneapp.data.response.LoginResponse
import com.example.cloneapp.presentasion.base.BaseViewModel
import com.example.cloneapp.presentasion.common.EmoneyError
import com.example.cloneapp.presentasion.common.SchedulerProvider
import io.reactivex.Observable

class LoginViewModel(application: Application, var authRemoteDataSource: AuthReposImpl) :
    BaseViewModel(application) {

    val data = MutableLiveData<Boolean>()

    fun login(request: LoginRequest) {
        subscriptions.add(
            Observable.just(request)
                .doOnNext {
                    verify(request.otp, request.pin)
                }
                .flatMap {
                    authRemoteDataSource.login(request.otp, request.pin)
                }
                .subscribeOn(SchedulerProvider.io())
                .observeOn(SchedulerProvider.ui())
                .subscribe(
                    this::onSuccess
                ) {
                    handleException(it)
                }
        )
    }

    fun verify(id: String, pas: String) {
        if (TextUtils.isEmpty(id) || TextUtils.isEmpty(pas))
            throw EmoneyError(context.getString(R.string.error))
    }

    private fun onSuccess(response: LoginResponse) {
        when (response.code) {
            SUCCESS -> {
                data.value = true
            }
            else -> {
                showError(response.message)
            }
        }
    }

}