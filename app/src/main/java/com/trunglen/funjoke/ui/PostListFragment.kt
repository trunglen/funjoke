package com.trunglen.funjoke.ui


import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.trunglen.funjoke.R
import com.trunglen.funjoke.adapter.OnPostItemClickListener
import com.trunglen.funjoke.adapter.PostRecyclerViewAdapter
import com.trunglen.funjoke.model.Post
import com.trunglen.funjoke.service.PostService
import com.trunglen.funjoke.ui.activity.HomeActivity
import com.trunglen.funjoke.ui.activity.PostDetailActivity
import com.trunglen.funjoke.x.PostListScrollListener
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
                val bundle = Bundle()
                bundle.putString("id", item.id)
                bundle.putString("title", item.title)
                bundle.putString("content", item.content)
                bundle.putString("cat_title", catTitle)
                val intent = Intent(activity, PostDetailActivity::class.java)
                intent.putExtra("post_detail", bundle)
                startActivity(intent)
            }
        })

        return inflater.inflate(R.layout.fragment_post_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        catID = arguments?.getString("cat_id") ?: ""
        catTitle = arguments?.getString("cat_title") ?: ""
        if (catTitle=="") catTitle = "Funjoke - Truyện cười hay nhất"
        setToolbar(catTitle)
        recyclerListPost.adapter = postAdapter
        recyclerListPost.layoutManager = LinearLayoutManager(activity)
        recyclerListPost.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
        recyclerListPost.addOnScrollListener(object:PostListScrollListener(){
            override fun onLoadMore() {
                Log.d("last_position","dasds")
                fetchPost()
            }

        })
    }

    override fun onResume() {
        super.onResume()
        this.page = 1
        fetchPost()
    }

    override fun onRefresh() {
        fetchPost()
    }

    private fun fetchPost() {
        PostService(activity).listPosts(catID, page).subscribe {
            val titles = it
            if (titles.isNotEmpty()) {
                postAdapter.items = titles as ArrayList<Post>
                recyclerListPost.adapter = postAdapter
                page++
            }
//            postAdapter.notifyDataSetChanged()
        }
    }

    fun setToolbar(title:String) {
        activity.title = title
    }
}
