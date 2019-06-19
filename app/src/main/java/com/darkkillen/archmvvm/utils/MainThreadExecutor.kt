package com.darkkillen.archmvvm.utils

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor

open class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }