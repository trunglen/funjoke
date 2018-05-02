package com.trunglen.funjoke.adapter

import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.trunglen.funjoke.R
import com.trunglen.funjoke.common.STATIC_POST_URL
import com.trunglen.funjoke.model.Post
import kotlinx.android.synthetic.main.list_post_item.view.*

class PostRecyclerViewAdapter(
        var items: ArrayList<Post>,
//        val context: Context,
        val listener: OnPostItemClickListener
) : RecyclerView.Adapter<ViewHolder>() {

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_post_item, parent, false))
    }

    // Binds each animal in the ArrayList to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = items.get(position)
//        holder.tvTitle.text = post.title
//        holder.tvDescription.text = Html.fromHtml(post.content)
//        Glide.with(context).load(STATIC_POST_URL + post.id)
//                .thumbnail(1.0f)
//                .crossFade()
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(holder.ivPost);
        holder.bind(listener,post)
    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val tvTitle = view.tvTitle
    val ivPost = view.icon_star
    val tvDescription = view.tvDescription

    fun bind(listener: OnPostItemClickListener, post: Post) {
        tvTitle.text = post.title
        tvDescription.text = Html.fromHtml(post.content)
        Glide.with(itemView.context).load(STATIC_POST_URL + post.id)
                .thumbnail(1.0f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivPost)
        itemView.setOnClickListener{
            listener.onItemClick(post)
        }
    }
}