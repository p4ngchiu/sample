package com.example.cloneapp.common.extension

import android.app.*
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.example.cloneapp.R
import com.example.cloneapp.utils.ProgressDialogUtils
import com.google.gson.Gson
import com.example.cloneapp.presentasion.base.ViewModelFactory
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.greenrobot.eventbus.EventBus
import java.util.*

fun Context.getColorByResource(@ColorRes res: Int): Int = ContextCompat.getColor(this, res)
fun Context.getDrawableByResource(@DrawableRes res: Int): Drawable? =
    ContextCompat.getDrawable(this, res)

/**
 * Runs a FragmentTransaction, then calls commit().
 */
inline fun FragmentManager.transact(action: FragmentTransaction.() -> Unit) {
    beginTransaction().apply {
        action()
    }.commit()
}

inline fun <reified T> Gson.fromJson(json: String): T = fromJson(json, T::class.java)


fun Context.pickDate(
    current: Calendar = Calendar.getInstance(),
    minDate: Calendar? = null,
    maxDate: Calendar? = null,
    onPick: (select: Calendar) -> Unit
) {
    DatePickerDialog(
        this,
        { _, year, monthOfYear, dayOfMonth ->
            current.set(Calendar.YEAR, year)
            current.set(Calendar.MONTH, monthOfYear)
            current.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            onPick.invoke(current)
        },
        current.get(Calendar.YEAR),
        current.get(Calendar.MONTH),
        current.get(Calendar.DAY_OF_MONTH)
    ).apply {
        minDate?.let {
            datePicker.minDate = it.timeInMillis
        }
        maxDate?.let {
            datePicker.maxDate = it.timeInMillis
        }
    }.show()
}

fun Context.pickTime(
    current: Calendar = Calendar.getInstance(),
    format24: Boolean = false,
    onPick: (select: Calendar) -> Unit
) {
    TimePickerDialog(this, { _, hour, minute ->
        current.set(Calendar.HOUR_OF_DAY, hour)
        current.set(Calendar.MINUTE, minute)
        onPick.invoke(current)
    }, current.get(Calendar.HOUR_OF_DAY), current.get(Calendar.MINUTE), format24).show()
}

fun View.setDrawableBackground(context: Context, drawable: Int) {
    background = ContextCompat.getDrawable(context, drawable)
}

inline fun <reified T : ViewModel> AppCompatActivity.initViewModel() =
    ViewModelProviders.of(this, ViewModelFactory.getInstance(this)).get(T::class.java)

inline fun <reified T : ViewModel> Fragment.initViewModel() =
    ViewModelProviders.of(this, ViewModelFactory.getInstance(this.context!!)).get(T::class.java)

fun Context.showDialogConfirmDefault(
    title: String? = null,
    content: String,
    ok: () -> Unit,
    cancel: () -> Unit
) {
    val builder = AlertDialog.Builder(this).apply {
        setTitle(title)
        setMessage(content)
        setPositiveButton(R.string.dialog_default_ok) { dialogInterface, i ->
            ok.invoke()
            dialogInterface.dismiss()
        }
        setNegativeButton(R.string.dialog_default_cancel) { dialogInterface, i ->
            dialogInterface.dismiss()
            cancel.invoke()
        }
    }
    builder.show()

}

fun Activity.registerEventBus(){
    if(!EventBus.getDefault().isRegistered(this)){
        EventBus.getDefault().register(this)
    }
}

fun Activity.unregisterEventBus(){
    if(EventBus.getDefault().isRegistered(this)){
        EventBus.getDefault().unregister(this)
    }
}

fun Fragment.registerEventBus(){
    if(!EventBus.getDefault().isRegistered(this)){
        EventBus.getDefault().register(this)
    }
}

fun Fragment.unregisterEventBus(){
    if(EventBus.getDefault().isRegistered(this)){
        EventBus.getDefault().unregister(this)
    }
}

fun Fragment.showProgressDialog(show: Boolean){
    if(show) ProgressDialogUtils.showProgressDialog(this.context)
    else ProgressDialogUtils.dismissProgressDialog()
}

fun Activity.showProgressDialog(show: Boolean){
    if(show) ProgressDialogUtils.showProgressDialog(this)
    else ProgressDialogUtils.dismissProgressDialog()
}

fun Disposable.add(subscriptions: CompositeDisposable){
    subscriptions.add(this)
}