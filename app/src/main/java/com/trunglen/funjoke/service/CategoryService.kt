package com.trunglen.funjoke.service

import android.content.Context
import com.trunglen.funjoke.FunjokeApplication
import com.trunglen.funjoke.model.Category
import io.reactivex.Observable

class CategoryService(context : Context):BaseService(context) {
    var categoryService: ICategoryService

    init {
        categoryService = FunjokeApplication.funJokeHttpService.create(ICategoryService::class.java)
    }

    fun listCategories(): Observable<List<Category>> {
        return this.request(categoryService.listCategories()).map {
            //            if (it.data!=null) it.data else emptyList()
            it.data?: emptyList()
        }
    }
}