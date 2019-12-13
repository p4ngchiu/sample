package com.example.cloneapp.presentasion.base

import com.example.cloneapp.R
import com.example.cloneapp.presentasion.common.MessageException
import com.hanmirae.emoney.data.exception.ApiException
import com.hanmirae.emoney.presentasion.widget.AppSnackbar
import io.reactivex.subjects.PublishSubject
import java.io.IOException


typealias ActionInterceptor<T> = (param: T) -> Boolean

typealias Action<T> = (param: T) -> Unit

class ExceptionHandler(
    private val e: Throwable,
    private val dialogPublisher: PublishSubject<AppSnackbar.Config>? = null
) {
    fun handle(action: ActionInterceptor<Throwable> = { false }) {
        when (e) {
            is ApiException -> processAction(e, action) { ex ->
                if (ex.code() == 401) {
//                    dialogPublisher?.onNext(
//                        AppSnackbar.Config.error(R.string.errer_authorized)
//                    )
                    dialogPublisher?.onNext(
                        AppSnackbar.Config.error(e.errorResponse.message!!)
                    )
                } else {
                    dialogPublisher?.onNext(
                        AppSnackbar.Config.error(e.errorResponse.message!!)
                    )
                }

            }

            is MessageException -> processAction(e, action) { ex ->
                ex.message?.let { message ->
                    dialogPublisher?.onNext(
                        AppSnackbar.Config.error(message)
                    )
                }
            }
            is IOException -> processAction(e, action) {
                dialogPublisher?.onNext(
                    AppSnackbar.Config.error(R.string.error_network_connection_error)
                )
            }
            else ->
                e.message?.let {
                    if (it.startsWith("Failed to connect to") || it.startsWith("Unable to resolve host")) dialogPublisher?.onNext(
                        AppSnackbar.Config.error(R.string.error_network)
                    ) else {
                        if (e.message != null) {
                            dialogPublisher?.onNext(AppSnackbar.Config.error(e.message!!))
                        } else
                            dialogPublisher?.onNext(AppSnackbar.Config.error("UnKnow error"))
                    }
                }

        }
    }

    private fun <E : Throwable> processAction(
        e: E,
        interceptor: ActionInterceptor<E>,
        action: Action<E>
    ) {
        if (!interceptor(e)) {
            action(e)
        }
    }
}