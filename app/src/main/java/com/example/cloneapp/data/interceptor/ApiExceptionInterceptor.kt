package com.example.cloneapp.data.interceptor

import com.hanmirae.emoney.data.exception.ApiException
import okhttp3.Interceptor
import okhttp3.Response

class ApiExceptionInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response: Response? = chain.proceed(request)
        if (!response?.isSuccessful!!) {
            throw ApiException(retrofit2.Response.error(response.body!!, response))
        }
        return response
    }
}