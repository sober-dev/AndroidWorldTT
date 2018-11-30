package com.testevents.androidworld.network

import com.testevents.androidworld.models.ContentData
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface SmartBoxApiService {

    @GET("tt/TT.json")
    fun getContent(): Observable<List<ContentData>>

    companion object Factory {
        fun create(): SmartBoxApiService {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://smartbox.software/")
                .client(client)
                .build()

            return retrofit.create(SmartBoxApiService::class.java)
        }
    }
}
