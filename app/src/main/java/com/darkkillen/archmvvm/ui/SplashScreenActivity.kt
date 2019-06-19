package com.darkkillen.archmvvm.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import com.darkkillen.archmvvm.ui.main.MainActivity
import com.darkkillen.archmvvm.utils.Status
import com.darkkillen.archmvvm.viewmodel.SplashScreenViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashScreenActivity : AppCompatActivity() {

    val TAG = javaClass.simpleName

    private val model: SplashScreenViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        model.checkAppAvailable().observe(this, Observer {
            when(it?.status) {
                Status.LOADING -> {

                }
                Status.SUCCESS -> {

                }
                Status.WARNING -> {
                    Toast.makeText(this, "Warning: ${it.message}", Toast.LENGTH_SHORT).show()
                }
                Status.UNAUTHORIZED -> {
                    Toast.makeText(this, "Unauthorized: ${it.message}", Toast.LENGTH_SHORT).show()
                }
                Status.ERROR -> {
                    Toast.makeText(this, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
                }
                Status.NETWORK -> {
                    Toast.makeText(this, "Network: ${it.message}", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(this, "Other: ${it?.message}", Toast.LENGTH_SHORT).show()
                }
            }
        })


//        Handler().postDelayed({
//            startActivity(Intent(this, MainActivity::class.java))
//            ActivityCompat.finishAffinity(this)
//        }, 2000)
    }

}