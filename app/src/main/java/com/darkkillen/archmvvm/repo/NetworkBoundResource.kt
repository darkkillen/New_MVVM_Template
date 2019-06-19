package com.darkkillen.archmvvm.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.darkkillen.archmvvm.utils.AppExecutors
import com.darkkillen.archmvvm.utils.NoNetworkException
import com.darkkillen.archmvvm.utils.ResponseErrorHandler
import com.darkkillen.archmvvm.vo.ResponseErrorBody
import retrofit2.Call
import retrofit2.Response

/**
 *
 * How to use:
 *
 * NetworkBoundResource(appExecutors, object : NetworkBoundResource.Callback<Register, Example>() {
 *      override fun onUnauthorized() {
 *          Timber.i("onUnauthorized: true")
 *      }
 *
 *      override fun onResponse(action: String, code: Int, message: String, item: Register?) {
 *          Timber.i("action:$action")
 *          Timber.i("code:$code")
 *          Timber.i("message:$message")
 *          Timber.i("item:$item")
 *      }
 *
 *      override fun onCreateCall(): Call<Register> = hotelConnectApi.register(register)
 *
 *      override fun onError(t: Throwable) {
 *          Timber.e("onError: ${t.message}")
 *          data.postValue(Resource.error(t.message!!, null))
 *      }
 *
 *      override fun onWarning(t: Throwable) {
 *          Timber.e("onWarning: ${t.message}")
 *          data.postValue(Resource.warning(t.message!!, null))
 *      }
 *
 *      override fun onNetworkUnavailable(t: NoNetworkException) {
 *          Timber.e("onNetworkUnavailable: ${t.message}")
 *          data.postValue(Resource.network(t.message!!, null))
 *      }
 *
 *      override fun enableLoadFromDb(): Boolean = NetworkBoundResource.LOAD_FROM_DB_ENABLE
 *
 *      override fun onLoadFromDb(): Example = appDatabase.userDao().findByEmail(register.email!!)
 *
 *      override fun onDatabaseResponse(item: Example?) {
 *          Timber.i("onDatabaseResponse:${item?.id}")
 *      }
 * })
 *
 */
class NetworkBoundResource<RequestType, EntityType> constructor(private val appExecutors: AppExecutors, private val callback: Callback<RequestType, EntityType>) {

    init {

        onLoadFromDb()

        fetchFromNetwork()
    }

    private fun fetchFromNetwork() {
        appExecutors.networkIO().execute {
            callback.onCreateCall().enqueue(object : retrofit2.Callback<RequestType> {
                override fun onResponse(call: Call<RequestType>?, response: Response<RequestType>) {
                    when (response.isSuccessful) {
                        true -> {
                            val body = response.body()
                            if (body != null) {
                                callback.onResponse(response.code(), response.message(), body)
                            } else {
                                callback.onError(Throwable(response.message()))
                            }
                        }
                        else -> {
                            when (response.code()) {
                                401 -> callback.onUnauthorized()
                                else -> {
                                    ResponseErrorHandler(response, object : ResponseErrorHandler.OnErrorListener<EntityType> {
                                        override fun onClientError(message: String, errorBody: ResponseErrorBody<EntityType>?) {
                                            var errorMessage = message
                                            if (null != errorBody) {
                                                errorMessage = errorBody.message
                                            }
                                            callback.onWarning(Throwable(errorMessage))
                                        }

                                        override fun onServerError(message: String, errorBody: ResponseErrorBody<EntityType>?) {
                                            callback.onError(Throwable(message))
                                        }
                                    })
                                }
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<RequestType>?, e: Throwable) {
                    if (e is NoNetworkException) {
                        // handle 'no network'
                        callback.onNetworkUnavailable(e)
                    } else {
                        // handle some other error
                        callback.onError(e)
                    }
                }
            })
        }
    }

    private fun onLoadFromDb() {
        appExecutors.diskIO().execute {
            if (callback.enableLoadFromDb()) {
                 callback.onLoadFromDb().observeForever { it -> callback.onDatabaseResponse(it) }
            }
        }
    }


    companion object {
        val LOAD_FROM_DB_DISABLE = false
        val LOAD_FROM_DB_ENABLE = true
    }

    abstract class Callback<RequestType, EntityType> {
        abstract fun onUnauthorized()
        abstract fun onCreateCall(): Call<RequestType>
        abstract fun onResponse(code: Int, message: String, remoteData: RequestType?)
        abstract fun onError(t: Throwable)
        abstract fun onWarning(t: Throwable)
        abstract fun onNetworkUnavailable(t: NoNetworkException)

        open fun onDatabaseResponse(item: EntityType?) {}

        open fun onLoadFromDb(): LiveData<EntityType> = MutableLiveData<EntityType>()

        open fun enableLoadFromDb(): Boolean = NetworkBoundResource.LOAD_FROM_DB_DISABLE
    }

}