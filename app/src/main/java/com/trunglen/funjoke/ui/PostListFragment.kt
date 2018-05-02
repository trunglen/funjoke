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
import kotlinx.android.synthetic.main.fragment_post_list.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class PostListFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener {


    var page = 1
    lateinit var postAdapter: PostRecyclerViewAdapter
    lateinit var catID: String
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

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        catID = arguments?.getString("cat_id") ?: ""
        recyclerListPost.adapter = postAdapter
        recyclerListPost.layoutManager = LinearLayoutManager(activity)
        recyclerListPost.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
        swipe_refresh_layout.setOnRefreshListener(this)
        swipe_refresh_layout.post(Runnable {
            swipe_refresh_layout.isRefreshing = true
            fetchPost()
        })
        // You can use GridLayoutManager if you want multiple columns. Enter the number of columns as a parameter
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
}
