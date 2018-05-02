package com.trunglen.funjoke.ui

import android.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.trunglen.funjoke.R
import java.util.*

open class BaseActivity() : AppCompatActivity() {
    val stackFragment = Stack<Fragment>()
    lateinit var currentFragment :BaseFragment


    fun nextFragment(fragment: BaseFragment, addToStack: Boolean = true) {
        if (addToStack)
            stackFragment.push(fragment)
        currentFragment = fragment
        fragmentManager.beginTransaction().replace(R.id.viewHolder, fragment).addToBackStack(null).commit()
    }

    fun skipFragment(fragment: BaseFragment) {
        stackFragment.pop()
        stackFragment.push(fragment)
        fragmentManager.beginTransaction().replace(R.id.viewHolder, fragment).addToBackStack("by_cat").commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (stackFragment.isNotEmpty()) {
            val backFragment = stackFragment.pop()
            fragmentManager.beginTransaction().replace(currentFragment.id, backFragment).disallowAddToBackStack().commit()
        } else {
            finish()
        }
//        if (stackFragment.isNotEmpty()) {
//            fragmentManager.popBackStack()
//        }
    }
}