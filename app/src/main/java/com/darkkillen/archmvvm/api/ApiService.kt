package com.darkkillen.archmvvm.api

import com.darkkillen.archmvvm.vo.maintenance.MaintenanceResponse
import retrofit2.Call
import retrofit2.http.POST

interface ApiService {

    @POST("androidMobileV2/maintenance.php")
    fun checkAvailable(): Call<MaintenanceResponse>

}