package com.trunglen.funjoke.service

import android.content.Context
import com.trunglen.funjoke.FunjokeApplication
import com.trunglen.funjoke.model.BaseModel
import com.trunglen.funjoke.model.Post
import io.reactivex.Observable
import retrofit2.Call

class PostService(context: Context) : BaseService(context) {
    var postService: IPostService

    init {
        postService = FunjokeApplication.funJokeHttpService.create(IPostService::class.java)
    }

    fun listPosts(page: Int): Observable<List<Post>> {
        return this.request(postService.listPosts(page)).map {
//            if (it.data!=null) it.data else emptyList()
            it.data?: emptyList()
        }
    }
}