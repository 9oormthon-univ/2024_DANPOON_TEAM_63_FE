package com.example.youthspacefinder.network

import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import retrofit2.Retrofit

object RetrofitInstance {
    private const val BASE_URL = "https://www.youthcenter.go.kr/"

    val networkService: NetworkService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(TikXmlConverterFactory.create(
                TikXml.Builder().exceptionOnUnreadXml(false).build()
            ))
            .build()
            .create(NetworkService::class.java)
    }
}