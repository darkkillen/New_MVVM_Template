package com.darkkillen.archmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.darkkillen.archmvvm.repo.MaintenanceRepo
import com.darkkillen.archmvvm.utils.Resource
import com.darkkillen.archmvvm.vo.maintenance.MaintenanceResponse

class SplashScreenViewModel(private val repo: MaintenanceRepo): ViewModel() {

    fun checkAppAvailable(): LiveData<Resource<MaintenanceResponse>> {
        return repo.checkAvailable()
    }

}