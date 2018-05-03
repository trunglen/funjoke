package com.trunglen.funjoke.ui


import android.app.Fragment
import android.os.Build
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.trunglen.funjoke.R
import com.trunglen.funjoke.adapter.OnPostItemClickListener
import com.trunglen.funjoke.adapter.PostRecyclerViewAdapter
import com.trunglen.funjoke.model.Post
import com.trunglen.funjoke.service.PostService
import kotlinx.android.synthetic.main.app_bar_home.*
import kotlinx.android.synthetic.main.fragment_post_list.*

class PostListFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener {


    var page = 1
    lateinit var postAdapter: PostRecyclerViewAdapter
    lateinit var catID: String
    lateinit var catTitle:String
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        postAdapter = PostRecyclerViewAdapter(ArrayList<Post>(), object : OnPostItemClickListener {
            override fun onItemClick(item: Post) {
                val postDetailFragment = PostDetailFragment()
                val bundle = Bundle()
                bundle.putString("id", item.id)
                bundle.putString("title", item.title)
                bundle.putString("content", item.content)
                postDetailFragment.arguments = bundle
                fragmentManager.beginTransaction().replace(id, postDetailFragment).commit()
                (activity as HomeActivity).nextFragment(postDetailFragment, false)
            }
        })

        return inflater.inflate(R.layout.fragment_post_list, container, false)
//        View.OnClickListener {
//            val postDetailFragment = PostDetailFragment()
//            postDetailFragment.arguments.putString("title",)
//            fragmentManager.beginTransaction().replace(id,PostDetailFragment()).commit()
//        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        catID = arguments?.getString("cat_id") ?: ""
        catTitle = arguments?.getString("cat_title") ?: ""
        setToolbar(catTitle)
        recyclerListPost.adapter = postAdapter
        recyclerListPost.layoutManager = LinearLayoutManager(activity)
        recyclerListPost.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
        swipe_refresh_layout.setOnRefreshListener(this)
        swipe_refresh_layout.post(Runnable {
            swipe_refresh_layout.isRefreshing = true
            fetchPost()
        })
    }

    override fun onResume() {
        super.onResume()
        Log.d("fragment_life","on_resume")
        this.page = 1
        fetchPost()
    }

    override fun onRefresh() {
        fetchPost()
    }

    private fun fetchPost() {
        swipe_refresh_layout.isRefreshing = true
        PostService(activity).listPosts(catID, page).subscribe {
            val titles = it
            if (titles.isNotEmpty()) {
                postAdapter.items = titles as ArrayList<Post>
                recyclerListPost.adapter = postAdapter
                page++
            }
            swipe_refresh_layout.isRefreshing = false
//            postAdapter.notifyDataSetChanged()
        }
    }

    fun setToolbar(title:String) {
        activity.toolbar.title = title
    }
}
