package com.trunglen.funjoke.ui

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import com.trunglen.funjoke.R
import com.trunglen.funjoke.service.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_home.*


class HomeActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var categoryService: CategoryService
    lateinit var postService: IPostService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        categoryService = CategoryService(this)
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
        addMenuItemInNavMenuDrawer()
        nextFragment(PostListFragment())
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val postFragment = PostListFragment()
        val bundle = Bundle()
        bundle.putString("cat_id", item.intent.extras.get("cat_id").toString())
        postFragment.arguments = bundle
        fragmentManager.beginTransaction().replace(R.id.viewHolder, postFragment).commit()
        skipFragment(postFragment)
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun addMenuItemInNavMenuDrawer() {
        val navView = nav_view
        val menu = navView.menu
        categoryService.listCategories().subscribe { categories ->
            categories.forEach { cat ->
                val catMenu = menu.add(cat.name)
                        .setIcon(R.drawable.ic_menu_send)
                val intent = Intent()
                intent.putExtra("cat_id", cat.id)
                catMenu.intent = intent
            }
        }
        navView.invalidate()
    }


}
