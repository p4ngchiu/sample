package com.example.cloneapp.data.base

import com.google.gson.annotations.SerializedName

open class BaseResponse {
    @SerializedName("status")
    var status: Int = 0
    @SerializedName("code")
    var code: String = ""
    @SerializedName("message")
    var message: String = ""
    @SerializedName("transId")
    var transId: String? = null
    @SerializedName("requireOtp")
    var requireOtp: Int? = null
    @SerializedName("expiredOtp")
    var expiredOtp: Int?=null
    @SerializedName("currency")
    var currency: Int = 0
    @SerializedName("balance")
    var balance: String? = null
}

