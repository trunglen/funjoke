package com.trunglen.funjoke.ui


import android.app.Fragment
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

import com.trunglen.funjoke.R
import com.trunglen.funjoke.common.STATIC_POST_URL
import kotlinx.android.synthetic.main.app_bar_home.*
import kotlinx.android.synthetic.main.fragment_post_detail.*
import kotlinx.android.synthetic.main.list_post_item.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class PostDetailFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post_detail, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        tvPostTitle.text = arguments?.getString("title") ?: ""
        tvPostContent.text = Html.fromHtml(arguments?.getString("content") ?: "")
        val postID = arguments?.getString("id") ?: ""
        Glide.with(activity).load(STATIC_POST_URL + postID)
                .thumbnail(1.0f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivPostThumb);
    }
}
