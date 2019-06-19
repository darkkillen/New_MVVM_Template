package com.darkkillen.archmvvm.ui.base

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.MemoryCategory

@SuppressLint("Registered")
open class BaseAppCompatActivity : AppCompatActivity() {

    private val TAG = BaseAppCompatActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Glide.get(this).setMemoryCategory(MemoryCategory.NORMAL)
    }

}