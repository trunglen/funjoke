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
import com.trunglen.funjoke.adapter.PostRecyclerViewAdapter
import com.trunglen.funjoke.model.Post
import com.trunglen.funjoke.service.PostService
import kotlinx.android.synthetic.main.fragment_post_list.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class PostListFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {


    var page = 1
    lateinit var postAdapter: PostRecyclerViewAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

//        PostService(activity).listPosts(1).subscribe {
//            //            val titles = it.map(Post::title) as ArrayList<String>
//            val titles = it
//            postAdapter = PostRecyclerViewAdapter(titles as ArrayList<Post>, activity)
//            recyclerListPost.adapter = postAdapter
//            swipe_refresh_layout.setOnRefreshListener {
//                object : Runnable {
//                    override fun run() {
//
//                    }
//
//                }
//            }
//        }
        // Inflate the layout for this fragment
        postAdapter = PostRecyclerViewAdapter(ArrayList<Post>(), activity)
        return inflater.inflate(R.layout.fragment_post_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerListPost.adapter = postAdapter
        recyclerListPost.layoutManager = LinearLayoutManager(activity)
        recyclerListPost.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
        swipe_refresh_layout.setOnRefreshListener(this)
        swipe_refresh_layout.post(Runnable {
            swipe_refresh_layout.isRefreshing = true
            fetchPost()
        })
//        recyclerListPost.layoutManager = LinearLayoutManager(activity)

        // You can use GridLayoutManager if you want multiple columns. Enter the number of columns as a parameter
    }

    override fun onRefresh() {
        fetchPost()
    }

    private fun fetchPost() {
        swipe_refresh_layout.isRefreshing = true
        PostService(activity).listPosts(page).subscribe {
            val titles = it
            Log.d("response_body", it.toString())
            if (titles.size!=0) {
                postAdapter = PostRecyclerViewAdapter(titles as ArrayList<Post>, activity)
                recyclerListPost.adapter = postAdapter
                page++
            }
            swipe_refresh_layout.isRefreshing = false
//            postAdapter.notifyDataSetChanged()
        }
    }
}
