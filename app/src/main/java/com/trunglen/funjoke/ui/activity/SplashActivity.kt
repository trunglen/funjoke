package com.trunglen.funjoke.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.trunglen.funjoke.R
import android.content.Intent
import android.widget.Toast
import com.trunglen.funjoke.x.FunjokeUtils


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!FunjokeUtils.isOnline(this)) {
            Toast.makeText(this,"Không có kết nối mạng. Vui lòng bật 3G hoặc Wifi",Toast.LENGTH_LONG).show()
        } else {
            startActivity(Intent(this, HomeActivity::class.java))
        }
        // close splash activity
        finish()
    }
}
