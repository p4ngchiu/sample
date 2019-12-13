package com.example.cloneapp.data.repository.run

import com.example.cloneapp.data.apiservice.AuthService
import com.example.cloneapp.data.base.AuthRepos
import com.example.cloneapp.data.request.LoginRequest
import com.example.cloneapp.data.response.LoginResponse
import io.reactivex.Observable

class AuthReposImpl private constructor(private val service: AuthService) : AuthRepos {

    override fun login(pin: String, otp: String): Observable<LoginResponse> = service.login(
        LoginRequest(pin, otp)
    )

    companion object {
        private var INSTANCE: AuthReposImpl? = null

        fun getInstance(service: AuthService): AuthReposImpl = INSTANCE
            ?: AuthReposImpl(service).also {
                INSTANCE = it
            }
    }
}