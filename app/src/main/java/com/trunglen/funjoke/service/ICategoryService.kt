package com.trunglen.funjoke.service

import com.trunglen.funjoke.model.BaseModel
import com.trunglen.funjoke.model.Category
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET

interface ICategoryService {
    @GET("public/category/list")
    fun listCategories(): Observable<Response<BaseModel<List<Category>>>>
}