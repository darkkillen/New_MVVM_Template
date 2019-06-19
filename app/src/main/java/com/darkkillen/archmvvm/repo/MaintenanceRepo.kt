package com.darkkillen.archmvvm.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.darkkillen.archmvvm.api.ApiService
import com.darkkillen.archmvvm.db.AppDatabase
import com.darkkillen.archmvvm.utils.AppExecutors
import com.darkkillen.archmvvm.utils.NoNetworkException
import com.darkkillen.archmvvm.utils.Resource
import com.darkkillen.archmvvm.vo.maintenance.MaintenanceResponse
import retrofit2.Call
import timber.log.Timber

class MaintenanceRepo(private val appExecutors: AppExecutors, private val appDatabase: AppDatabase, private val apiService: ApiService) {

    fun checkAvailable(): LiveData<Resource<MaintenanceResponse>> {
        val data = MutableLiveData<Resource<MaintenanceResponse>>()
        data.postValue(Resource.loading(null))
        NetworkBoundResource(appExecutors, object : NetworkBoundResource.Callback<MaintenanceResponse, MaintenanceResponse>() {

            override fun onUnauthorized() {
                data.postValue(Resource.unauthorized("", null))
            }

            override fun onCreateCall(): Call<MaintenanceResponse> {
                return apiService.checkAvailable()
            }

            override fun onResponse(code: Int, message: String, remoteData: MaintenanceResponse?) {
                when (code) {
                    in 200..201 -> {
                        if (remoteData != null) {
                            appExecutors.diskIO().execute {
                                data.postValue(Resource.success(remoteData))
                            }
                        } else {
                            data.postValue(Resource.error(message, null))
                        }
                    }
                    else -> {
                        data.postValue(Resource.error(message, null))
                    }
                }
            }

            override fun onError(t: Throwable) {
                Timber.e("onError: ${t.message}")
                data.postValue(Resource.error(t.message!!, null))
            }

            override fun onWarning(t: Throwable) {
                Timber.e("onWarning: ${t.message}")
                data.postValue(Resource.warning(t.message!!, null))
            }

            override fun onNetworkUnavailable(t: NoNetworkException) {
                Timber.e("onNetworkUnavailable: ${t.message}")
                data.postValue(Resource.network(t.message!!, null))
            }

            override fun enableLoadFromDb(): Boolean = NetworkBoundResource.LOAD_FROM_DB_DISABLE

        })

        return data
    }

}