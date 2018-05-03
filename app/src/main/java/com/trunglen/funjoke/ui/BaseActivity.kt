package com.trunglen.funjoke.ui

import android.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.google.android.gms.ads.AdRequest
import com.trunglen.funjoke.R
import kotlinx.android.synthetic.main.app_bar_home.*
import java.util.*
import com.google.android.gms.ads.AdView



open class BaseActivity() : AppCompatActivity() {
    val stackFragment = Stack<Fragment>()
    lateinit var currentFragment :BaseFragment


    fun nextFragment(fragment: BaseFragment) {
        currentFragment = fragment
        fragmentManager.beginTransaction().replace(R.id.viewHolder, fragment).addToBackStack(null).commit()
    }

    fun skipBackFragment(fragment: BaseFragment) {
        fragmentManager.beginTransaction().replace(R.id.viewHolder, fragment).commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
//        if (stackFragment.isNotEmpty()) {
//            fragmentManager.popBackStack()
//        }
    }

    fun setToolbarTitle(title:String){
        setSupportActionBar(toolbar)
    }

    fun requestAds() {
        val banner = findViewById<View>(R.id.banner) as AdView
        val adRequest = AdRequest.Builder().build()
        banner.loadAd(adRequest)
    }
}