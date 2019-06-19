package com.darkkillen.archmvvm.utils

import com.google.gson.Gson
import com.darkkillen.archmvvm.vo.ResponseErrorBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException

open class ResponseErrorHandler<RequestType, EntityType>(response: Response<RequestType>, listener: OnErrorListener<EntityType>?) {

    init {

        val errorBody = mapToObject(response)

        when (response.code()) {
            in 402..499 -> {
                listener?.onClientError(response.message(), errorBody)
            }
            in 501..599 -> {
                listener?.onServerError(response.message(), errorBody)
            }
        }
    }

    private fun mapToObject(response: Response<RequestType>): ResponseErrorBody<EntityType>? {
        return try {
            val objError = JSONObject(response.errorBody()?.string())
            Gson().fromJson<ResponseErrorBody<EntityType>>(objError.toString(), ResponseErrorBody::class.java)
        } catch (e: JSONException) {
            e.printStackTrace()
            null
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    private fun getErrorMessage(response: Response<RequestType>) :String {
        return try {
            val objError = JSONObject(response.errorBody()?.string())
            val obj = objError.getJSONObject(Constant.ERROR_KEY)
            val msg = obj.getString(Constant.ERROR_MESSAGE_KEY)
            val message = msg.replace("\\[".toRegex(), "").replace("\\]".toRegex(), "")
            message
        } catch (e: JSONException) {
            e.printStackTrace()
            response.message()
        } catch (e: IOException) {
            e.printStackTrace()
            response.message()
        }
    }

    interface OnErrorListener<T> {

        fun onClientError(message: String, errorBody: ResponseErrorBody<T>?)

        fun onServerError(message: String, errorBody: ResponseErrorBody<T>?)
    }

}