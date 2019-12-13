package com.example.cloneapp.data.response

import com.example.cloneapp.data.base.BaseResponse
import com.google.gson.annotations.SerializedName

class LoginResponse : BaseResponse() {
    @SerializedName("notificationTopics")
    var notificationTopics: List<String>? = null
    @SerializedName("accessToken")
    var accessToken: String? = null
}