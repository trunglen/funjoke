package com.trunglen.funjoke.service

import android.content.Context
import android.widget.Toast
import com.trunglen.funjoke.model.BaseModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers


//class BaseService<T>(val cal:Call<T>, val context: Context) {
//    companion object {
//    }
//    fun request(callback:(T?)->Unit) {
//        cal.enqueue(object :Callback<T> {
//            override fun onFailure(call: Call<T>?, t: Throwable?) {
//                Toast.makeText(context,"Lỗi kết nối đến server",Toast.LENGTH_LONG).show()
//            }
//
//            override fun onResponse(call: Call<T>?, response: Response<T>?) {
//                if (response?.code() != 200) {
//                    Toast.makeText(context,"Lỗi kết nối đến server",Toast.LENGTH_LONG).show()
//                } else {
//                    callback(response.body())
//                }
//            }
//        })
//    }
//}

class BaseService<T>(val obs: Observable<BaseModel<T>>, val context: Context) {
    companion object {
    }

    fun request(callback: (T?) -> Unit) {
        obs.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Consumer {
                    callback(it.data)
                }, Consumer {
                    Toast.makeText(context, "Lỗi kết nối đến server", Toast.LENGTH_LONG).show()
                })
    }
}