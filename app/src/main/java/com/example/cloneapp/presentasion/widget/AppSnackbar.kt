package com.hanmirae.emoney.presentasion.widget

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import com.example.cloneapp.R
import com.google.android.material.snackbar.Snackbar
import com.example.cloneapp.common.extension.getColorByResource

class AppSnackbar
private constructor(
    parent: View,
    config: Config
) {

    private lateinit var titleView: TextView

    private lateinit var textView: TextView

    private val snackbar: Snackbar = Snackbar.make(parent, "", config.duration).apply {
        (view as Snackbar.SnackbarLayout).run {
            setPadding(0, 0, 0, 0)
            findViewById<View>(R.id.snackbar_text).visibility = View.INVISIBLE
            addView(
                LayoutInflater.from(context).inflate(R.layout.snackbar, this, false).apply {
                    titleView = findViewById<TextView>(R.id.title).apply(config.titleInit).apply(config.colorInit)
                    textView = findViewById<TextView>(R.id.text).apply(config.textInit)
                }
                , 0
            )
        }
    }

    @Deprecated("Use AppSnackbar.Config instead.", ReplaceWith("Config().titleColor(color)"))
    fun titleColor(color: Int): AppSnackbar = apply { titleView.setTextColor(color) }

    fun show() = snackbar.show()

    class Config private constructor(
        internal val titleInit: TextView.() -> Unit,
        internal val colorInit: TextView.() -> Unit,
        internal val textInit: TextView.() -> Unit,
        internal val duration: Int
    ) {
        constructor(): this({}, {}, {}, LENGTH_SHORT)

        internal constructor(colorInit: TextView.() -> Unit)
                : this({}, colorInit, {}, LENGTH_SHORT)

        fun title(@StringRes res: Int): Config = Config({ setText(res) }, colorInit, textInit, duration)

        fun title(s: String): Config = Config({ text = s }, colorInit, textInit, duration)

        fun  titleColor(c: Int): Config = Config(titleInit, { setTextColor(c) }, textInit, duration)

        fun text(@StringRes res: Int): Config {
            return Config(titleInit, colorInit, { setText(res) }, duration)
        }

        fun text(s: String): Config = Config(titleInit, colorInit, { text = s }, duration)

        fun duration(d: Int): Config = Config(titleInit, colorInit, textInit, d)

        companion object {

            private val errorConfig =
                Config(colorInit = { setTextColor(context.getColorByResource(R.color.halation_red)) })
                    .title(R.string.snackbar_error_title)
                    .duration(LENGTH_LONG)

            private val successConfig =
                Config(colorInit = { })
                    .duration(LENGTH_LONG)

            fun error(@StringRes res: Int): Config = errorConfig.text(res)

            fun success(@StringRes res: Int): Config = successConfig.text(res)

            fun error(message: String): Config = errorConfig.text(message)

            fun success( res: String): Config = successConfig.text(res)
        }

    }

    companion object {
        const val TAG = "AppSnackbar"

        const val LENGTH_SHORT = Snackbar.LENGTH_SHORT
        const val LENGTH_LONG = Snackbar.LENGTH_LONG

        fun make(view: View, @StringRes titleRes: Int, @StringRes textRes: Int, duration: Int = LENGTH_SHORT): AppSnackbar =
            AppSnackbar(
                view,
                Config().title(titleRes)
                    .text(textRes)
                    .duration(duration)
            )

        fun make(view: View, config: Config) = AppSnackbar(view, config)
    }

}