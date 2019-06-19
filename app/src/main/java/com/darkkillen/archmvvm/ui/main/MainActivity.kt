package com.darkkillen.archmvvm.ui.main

import android.os.Bundle
import com.darkkillen.archmvvm.R
import com.darkkillen.archmvvm.ui.base.BaseAppCompatActivity

class MainActivity : BaseAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {

        }
    }
}
