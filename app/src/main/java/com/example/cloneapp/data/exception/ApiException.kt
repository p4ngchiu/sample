package com.hanmirae.emoney.data.exception

import com.example.cloneapp.data.exception.ApiError
import retrofit2.HttpException

class ApiException(response: retrofit2.Response<out Any>) : HttpException(response) {
    var errorResponse: ApiError
    init {
        val json = response.errorBody()?.string()
        errorResponse = try {
            ApiError(message = json)
        } catch (e: Exception) {
            ApiError(message = e.message.toString())
        }
    }
}