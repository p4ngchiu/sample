package com.example.cloneapp.common.extension

import android.app.Activity

import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager


fun Activity.startActivity(activityClass: Class<*>, action: Intent.() -> Unit = { }) {
    startActivity(Intent(this, activityClass).apply {
        action()
    })
}
fun Activity.startActivityForResult(activityClass: Class<*>, requestCode: Int, action: Intent.() -> Unit = { }) {
    startActivityForResult(Intent(this, activityClass).apply(action), requestCode)
}


fun Activity.hideKeyboard() {
    val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    //Find the currently focused view, so we can grab the correct window token from it.
    var view = this.currentFocus
    //If no view currently has focus, create a new one, just so we can grab a window token from it
    if (view == null) {
        view = View(this)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}








