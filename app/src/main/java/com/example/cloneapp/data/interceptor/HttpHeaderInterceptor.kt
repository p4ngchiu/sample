package com.example.cloneapp.data.interceptor

import android.util.Log
import com.example.cloneapp.common.App
import okhttp3.Interceptor
import okhttp3.Response

class HttpHeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .header("Content-Type", "application/json")
            .header("e-language", App.get().storeData.language!!)
        App.get().storeData.eInfo?.let {
            request.header("e-info",it)
            Log.d("HttpHeaderInterceptor","einfo: $it")
        }
        App.get().storeData.accessToken?.let {
            request.header("Authorization",it)
            Log.d("HttpHeaderInterceptor","accesstoken: $it")
        }

        return chain.proceed(request.build())
    }
}