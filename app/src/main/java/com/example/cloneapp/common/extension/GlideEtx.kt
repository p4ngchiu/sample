package com.example.cloneapp.common.extension

import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.request.RequestOptions
import com.example.cloneapp.BuildConfig
import com.example.cloneapp.R

fun ImageView.loadImage(avatar: Any?) {
    val circularProgressDrawable = CircularProgressDrawable(context)
    circularProgressDrawable.backgroundColor = ContextCompat.getColor(context, R.color.main_red)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.setColorSchemeColors(
        ContextCompat.getColor(
            context,
            R.color.button_default
        )
    )
    circularProgressDrawable.start()
    val options = RequestOptions()
        .centerCrop()
        .placeholder(circularProgressDrawable)
        .error(R.drawable.ic_icon_setting)
        .priority(Priority.HIGH)

    Glide
        .with(context)
        .load(avatar)
        .apply(options)
        .into(this)
}

fun ImageView.loadImageServer(avatar: Any?) {
    val circularProgressDrawable = CircularProgressDrawable(context)
    circularProgressDrawable.backgroundColor = ContextCompat.getColor(context, R.color.main_red)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.setColorSchemeColors(
        ContextCompat.getColor(
            context,
            R.color.button_default
        )
    )
    circularProgressDrawable.start()
    val options = RequestOptions()
        .centerCrop()
        .placeholder(circularProgressDrawable)
        .error(R.drawable.ic_icon_setting)
        .priority(Priority.HIGH)

    Glide
        .with(context)
        .load("link")
        .apply(options)
        .into(this)
}

