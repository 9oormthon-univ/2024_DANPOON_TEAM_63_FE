package com.example.youthspacefinder.network

import AmenitiesResponse
import PositionResponse
import RegisterUserInfo
import SpacesInfoResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface NetworkService {
    @GET("opi/wantedSpace.do")
    fun getYouthSpaceList(
        @Query("openApiVlak") apiKey: String,
        @Query("srchAreaCpvn") cityCode: String = "", // 시,도 코드
        @Query("srchAreaSggn") areaCode: String = "" // 시,군,구 코드 → 사용자가 입력을 안할 경우를 생각해서 기본값 설정
    ): Call<SpacesInfoResponse>

    @GET("api/search-food")
    fun getAmenitiesList(
        @Query("address") address: String
    ): Call<List<AmenitiesResponse>>

    @GET("api/search-position")
    fun getLocationXY(
        @Query("address") address: String
    ): Call<PositionResponse>

    @POST("api/auth/register")
    fun registerUserInfo(
        @Body registerUserInfo: RegisterUserInfo
    ): Call<Any>
}