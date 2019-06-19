package com.darkkillen.archmvvm.di

import com.darkkillen.archmvvm.api.ApiService
import com.darkkillen.archmvvm.api.ServiceGenerator
import com.darkkillen.archmvvm.jni.JniHelper
import com.darkkillen.archmvvm.utils.AppExecutors
import com.darkkillen.archmvvm.utils.LiveNetworkMonitor
import com.darkkillen.archmvvm.utils.MainThreadExecutor
import com.darkkillen.archmvvm.vo.Config
import okhttp3.HttpUrl
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.util.concurrent.Executor
import java.util.concurrent.Executors

val remoteModule = module {
    single { LiveNetworkMonitor(androidContext()) }
    single(named("EXECUTORS_DISK_IO")) { Executors.newSingleThreadExecutor() }
    single(named("EXECUTORS_NETWORK_IO")) { Executors.newFixedThreadPool(3) }
    single(named("EXECUTORS_MAIN_THREAD")) { MainThreadExecutor() }
    single { AppExecutors(get(named("EXECUTORS_DISK_IO")), get(named("EXECUTORS_NETWORK_IO")), get(named("EXECUTORS_MAIN_THREAD"))) }
    factory(named("DEFAULT")) { ServiceGenerator.create(
        androidContext(),
        get(),
        Config(
            HttpUrl.parse(JniHelper.apiService()) as HttpUrl
        ),
        ApiService::class.java
    ) }
    factory(named("MAINTENANCE")) { ServiceGenerator.create(
        androidContext(),
        get(),
        Config(
            HttpUrl.parse(JniHelper.apiServiceMaintenance()) as HttpUrl
        ),
        ApiService::class.java
    ) }
}