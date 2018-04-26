package com.trunglen.funjoke

import android.app.Application
import com.trunglen.funjoke.common.BASE_URL
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class FunjokeApplication : Application() {
    companion object {
        lateinit var funJokeHttpService: Retrofit
    }

    override fun onCreate() {
        super.onCreate()
        funJokeHttpService = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(
                        GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
    }
}