package com.example.cloneapp.data.base

import com.example.cloneapp.data.apiservice.AuthService
import com.example.cloneapp.data.repository.run.AuthReposImpl


fun AuthReposImpl.Companion.getInstance() =
    getInstance(RetrofitClient.getInstance()[AuthService::class.java])
