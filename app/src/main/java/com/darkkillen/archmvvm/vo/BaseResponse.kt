package com.darkkillen.archmvvm.vo

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
        @SerializedName("code") var code: String,
        @SerializedName("message") var message: String,
        @SerializedName("meta") var meta: Map<String, Any>,
        @SerializedName("data") var data: T
)