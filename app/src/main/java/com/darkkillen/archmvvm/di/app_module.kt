package com.darkkillen.archmvvm.di

import com.darkkillen.archmvvm.repo.MaintenanceRepo
import com.darkkillen.archmvvm.viewmodel.SplashScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {

    single { MaintenanceRepo(get(), get(), get(named("MAINTENANCE"))) }

    viewModel { SplashScreenViewModel(get()) }
}