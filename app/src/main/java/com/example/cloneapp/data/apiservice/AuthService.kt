package com.example.cloneapp.data.apiservice

import com.example.cloneapp.data.request.LoginRequest
import com.example.cloneapp.data.response.LoginResponse
import io.reactivex.Observable
import retrofit2.http.*

interface AuthService {
    @POST("/auth/login")
    fun login(@Body request: LoginRequest): Observable<LoginResponse>
}