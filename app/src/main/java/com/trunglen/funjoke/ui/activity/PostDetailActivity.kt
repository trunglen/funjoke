package com.trunglen.funjoke.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.trunglen.funjoke.R
import com.trunglen.funjoke.common.STATIC_POST_URL
import com.trunglen.funjoke.ui.BaseActivity
import kotlinx.android.synthetic.main.app_bar_home.*
import kotlinx.android.synthetic.main.fragment_post_detail.*

class PostDetailActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_detail)
        val bundle = intent.getBundleExtra("post_detail")
        tvPostTitle.text = bundle?.getString("title") ?: ""
        tvPostContent.text = Html.fromHtml(bundle?.getString("content") ?: "")
        var catTitle = bundle?.getString("cat_title")
        if (catTitle=="") catTitle =  "Truyện cười mới nhất"
        title = catTitle
        val postID = bundle?.getString("id") ?: ""
        Glide.with(this).load(STATIC_POST_URL + postID)
                .thumbnail(1.0f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivPostThumb);
    }
}
