package com.trunglen.funjoke.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.trunglen.funjoke.R
import android.content.Intent



class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, HomeActivity::class.java))
        // close splash activity
        finish()
    }
}
