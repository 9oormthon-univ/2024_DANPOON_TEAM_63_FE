package com.example.youthspacefinder.network

import SpacesInfoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {
    @GET("opi/wantedSpace.do")
    fun getYouthSpaceList(
        @Query("openApiVlak") apiKey: String,
        @Query("srchAreaCpvn") regionCode: String
    ): Call<SpacesInfoResponse>
}