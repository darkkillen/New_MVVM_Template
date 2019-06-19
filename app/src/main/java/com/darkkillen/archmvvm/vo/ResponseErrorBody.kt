package com.darkkillen.archmvvm.vo

import com.google.gson.annotations.SerializedName

data class ResponseErrorBody<T>(
		@SerializedName("code") var code: String,
		@SerializedName("message") var message: String,
		@SerializedName("data") var data: T
)