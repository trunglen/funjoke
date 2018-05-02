package com.trunglen.funjoke.service

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.trunglen.funjoke.model.BaseModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import retrofit2.Response


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

open class BaseService(var context: Context) {
    companion object {
    }

    fun <T> request(obs: Observable<Response<T>>): Observable<T> {
        return obs.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError {
                    Log.d("response_error", it.message)
                }
                .map {
                    if (it.code() != 200) {
                        Toast.makeText(context, "Lỗi kết nối đến server", Toast.LENGTH_LONG).show()
                    }
                    it.body()
                }
    }
}