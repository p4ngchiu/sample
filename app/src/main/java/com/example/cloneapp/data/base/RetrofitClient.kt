package com.example.cloneapp.data.base

import com.example.cloneapp.BuildConfig
import com.example.cloneapp.data.interceptor.ApiExceptionInterceptor
import com.example.cloneapp.data.interceptor.HttpHeaderInterceptor
import com.google.gson.GsonBuilder
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient(baseUrl: String = BASE_URL) {
    private val retrofit: Retrofit by lazy {
        val client = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(headerLog)
            .addInterceptor(bodyLog)
            .addInterceptor(ApiExceptionInterceptor())
            .addInterceptor(HttpHeaderInterceptor())
            .addInterceptor(
                LoggingInterceptor.Builder()
                .loggable(BuildConfig.DEBUG)
                .setLevel(Level.BASIC)
                .log(Platform.INFO)
                .request("Request")
                .response("Response")
                .addHeader("version", BuildConfig.VERSION_NAME)
                .build())

        if(BuildConfig.DEBUG){
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            client.addInterceptor(logging)
        }
        Retrofit.Builder()
            .client(client.build())
            .baseUrl(baseUrl)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(rxJava2CallAdapterFactory)
            .build()
    }
    private val interfaceCache = HashMap<Class<*>, Any>()

    @Suppress("UNCHECKED_CAST")
    operator fun <T : Any> get(classOfService: Class<T>): T =
        (interfaceCache[classOfService] as? T) ?: retrofit.create(classOfService).also {
            interfaceCache[classOfService] = it
        }

    companion object {
        private const val TIMEOUT = 60L
       private const val ISO_8601_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ"
        private var INSTANCE: RetrofitClient? = null

        fun getInstance(): RetrofitClient = INSTANCE ?: RetrofitClient().also {
            INSTANCE = it
        }


        const val BASE_URL = "link"
        private val gsonConverterFactory = GsonConverterFactory.create(
            GsonBuilder().setDateFormat(ISO_8601_DATE_TIME_FORMAT).serializeNulls()
                .create()
        )
        private val rxJava2CallAdapterFactory = RxJava2CallAdapterFactory.create()
        private val headerLog = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.HEADERS }
        private val bodyLog = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    }

}