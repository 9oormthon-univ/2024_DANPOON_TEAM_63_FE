package com.example.youthspacefinder.network

import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://www.youthcenter.go.kr/"
    private const val BASE_URL_BACK_END = "http://43.203.233.143:8080/api/"

    val networkService: NetworkService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(TikXmlConverterFactory.create(
                TikXml.Builder().exceptionOnUnreadXml(false).build()
            ))
            .build()
            .create(NetworkService::class.java)
    }

    val networkServiceAmenities: NetworkService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_BACK_END)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NetworkService::class.java)
    }
}