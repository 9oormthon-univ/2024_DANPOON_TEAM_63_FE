package com.example.youthspacefinder.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitInstance {
    private const val BASE_URL_OPEN_API = "https://www.youthcenter.go.kr/"
    private const val BASE_URL_BACK_END = "http://13.125.8.99:8080/"

    val networkServiceOpenAPI: NetworkService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_OPEN_API)
            .addConverterFactory(TikXmlConverterFactory.create(
                TikXml.Builder().exceptionOnUnreadXml(false).build()
            ))
            .build()
            .create(NetworkService::class.java)
    }

    private val lenientGson: Gson = GsonBuilder().setLenient().create()

    val networkServiceBackEnd: NetworkService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_BACK_END)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(lenientGson))
            .build()
            .create(NetworkService::class.java)
    }
}