package com.example.cloneapp.data.base

import com.example.cloneapp.data.response.LoginResponse
import io.reactivex.Observable

interface AuthRepos {
    fun login( pin: String, otp: String): Observable<LoginResponse>
}