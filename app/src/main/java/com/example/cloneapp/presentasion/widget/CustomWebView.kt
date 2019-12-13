/*

 */
package com.example.cloneapp.presentasion.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.webkit.*
import com.example.cloneapp.R
import com.example.cloneapp.utils.ProgressDialogUtils


/**
 * Created by cantran on 5/18/17.
 */
class CustomWebView : WebView {


    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        initView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initView() {
        this.settings.javaScriptEnabled = true
        this.settings.defaultTextEncodingName = "utf-8"
        this.scrollBarStyle = SCROLLBARS_OUTSIDE_OVERLAY
        this.settings.cacheMode = WebSettings.LOAD_DEFAULT
        this.settings.setAppCacheEnabled(true)
        this.webViewClient = CustomWebViewClient()
    }

    /**
     * Sets the WebViewClient that will receive various notifications and
     * requests. This will replace the current handler.
     *
     * @author cantv
     */
    private inner class CustomWebViewClient : WebViewClient() {

        internal lateinit var url: String

        override fun onPageStarted(
            view: WebView, url: String,
            favicon: Bitmap?
        ) {
            super.onPageStarted(view, url, favicon)
            context?.let {
                ProgressDialogUtils.showProgressDialog(it)
            }
            if (!url.startsWith("data:text/html"))
                this.url = url
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            ProgressDialogUtils.dismissProgressDialog()

        }

        override fun onReceivedError(
            view: WebView,
            request: WebResourceRequest,
            error: WebResourceError
        ) {
            super.onReceivedError(view, request, error)
            ProgressDialogUtils.dismissProgressDialog()

            view.loadData(
                context.getString(R.string.webview_error    ),
                "text/html; charset=utf-8",
                "utf-8"
            )

            view.loadUrl("about:blank")
        }
    }

}
