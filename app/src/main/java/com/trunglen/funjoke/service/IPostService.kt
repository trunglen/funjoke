package com.trunglen.funjoke.service

import com.trunglen.funjoke.model.BaseModel
import com.trunglen.funjoke.model.Post
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface IPostService {
    @GET("public/post/list")
    fun listPosts(@Query("page") page: Int): Observable<Response<BaseModel<List<Post>>>>

    @GET("public/post/list")
    fun listPosts2(): Call<Response<List<Post>>>
}