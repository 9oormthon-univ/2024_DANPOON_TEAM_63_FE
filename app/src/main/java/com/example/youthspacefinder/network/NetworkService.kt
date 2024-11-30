package com.example.youthspacefinder.network

import AmenitiesResponse
import PositionResponse
import RegisterUserInfoRequest
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


    /*
    [feat] 회원가입 서버 통신
- 500 Status 에러 발생 → 서버가 application/json 형식의 요청을 예상하는데, 클라이언트에서 application/x-www-form-urlencoded 형식으로 요청을 보냄
- Retrofit에서 application/json으로 요청을 보내려면 @Body를 사용해야함
     */
    @POST("api/auth/register")
    fun registerUserInfo(
        @Body registerUserInfoRequest: RegisterUserInfoRequest
    ): Call<Any>

    @GET("api/search-food")
    fun getAmenitiesList(
        @Query("address") address: String
    ): Call<List<AmenitiesResponse>>

    @GET("api/search-position")
    fun getLocationXY(
        @Query("address") address: String
    ): Call<PositionResponse>
}