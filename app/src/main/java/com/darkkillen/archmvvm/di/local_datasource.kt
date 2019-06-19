package com.darkkillen.archmvvm.di

import com.darkkillen.archmvvm.db.AppDatabase
import com.darkkillen.archmvvm.prefs.PrefsHelper
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localSourceModule = module {
    single { AppDatabase.create(androidContext(), false) }
    single { PrefsHelper(androidContext()) }
}