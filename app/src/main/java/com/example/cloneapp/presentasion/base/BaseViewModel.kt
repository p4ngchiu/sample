package com.example.cloneapp.presentasion.base

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.hanmirae.emoney.presentasion.widget.AppSnackbar
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {
    val context: Context = application.applicationContext
    val subscriptions: CompositeDisposable by lazy { CompositeDisposable() }

    private val dialogConfig: PublishSubject<AppSnackbar.Config> =
        PublishSubject.create<AppSnackbar.Config>()

    fun getSnackBarConfig(): Observable<AppSnackbar.Config> = dialogConfig

    protected fun showSnackBarError(message: String) {
        dialogConfig.onNext(AppSnackbar.Config.error(message))
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.clear()
    }

    protected fun handleException(e: Throwable, action: ActionInterceptor<Throwable> = { false }) {
        val handler = ExceptionHandler(e, dialogConfig)
        handler.handle(action)
    }

    protected fun showError(message: String) {
        dialogConfig.onNext(
            AppSnackbar.Config.error(message)
        )
    }


    companion object {
        const val SUCCESS = "MSG_SUCCESS"
    }
}