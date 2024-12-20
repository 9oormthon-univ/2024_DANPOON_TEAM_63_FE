package com.example.youthspacefinder.network

import AmenitiesResponse
import FavoriteSpaceRequest
import PositionResponse
import ReservationResponse
import SpacesInfoResponse
import com.example.youthspacefinder.model.FavoriteSpaceResponse
import com.example.youthspacefinder.model.LoginUserInfo
import com.example.youthspacefinder.model.RegisterUserInfo
import com.example.youthspacefinder.model.ReviewRequest
import com.example.youthspacefinder.model.ReviewResponse
import com.example.youthspacefinder.model.UserNicknameRequest
import com.example.youthspacefinder.model.UserPasswordRequest
import com.example.youthspacefinder.model.UserTokenResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkService {
    @GET("opi/wantedSpace.do")
    fun getYouthSpaceList(
        @Query("openApiVlak") apiKey: String,
        @Query("srchAreaCpvn") cityCode: String = "", // 시,도 코드
        @Query("srchAreaSggn") areaCode: String = "", // 시,군,구 코드 → 사용자가 입력을 안할 경우를 생각해서 기본값 설정
        @Query("srchSpcId") spaceId: String = "",
        @Query("pageType") pageType: Int = 1
    ): Call<SpacesInfoResponse>

    @GET("api/search-facilities")
    fun getAmenitiesList(
        @Query("address") address: String,
        @Query("category") category: String
    ): Call<List<AmenitiesResponse>>

    @GET("api/search-position")
    fun getLocationXY(
        @Query("address") address: String
    ): Call<PositionResponse>

    @POST("api/auth/register")
    fun registerUserInfo(
        @Body registerUserInfo: RegisterUserInfo
    ): Call<Any>

    @POST("api/auth/login")
    fun checkUserLoginInfo(
        @Body loginUserInfo: LoginUserInfo
    ): Call<UserTokenResponse>

    @PUT("api/users/nickname")
    fun changeUserNickname(
        @Header("Authorization") token: String,
        @Body newNickname: UserNicknameRequest // json 형태로 보낼려면 data class 에 담아야한다.
    ): Call<Any>

    @PUT("api/users/password")
    fun changeUserPassword(
        @Header("Authorization") token: String,
        @Body passwordRequest: UserPasswordRequest
    ): Call<Any>

    @POST("api/reviews")
    fun registerSpaceReview(
        @Header("Authorization") token: String,
        @Body reviewRequest: ReviewRequest
    ): Call<ReviewResponse>

    @GET("api/showReviews")
    fun getSpaceReviews(
        @Query("youthSpaceId") youthSpaceId: Long
    ): Call<ArrayList<ReviewResponse>>

    @DELETE("api/reviews/{reviewId}")
    fun deleteSpaceReview(
        @Header("Authorization") token: String,
        @Path("reviewId") reviewId: Long
    ): Call<Any>

    @PUT("api/reviews/{reviewId}")
    fun modifySpaceReview(
        @Header("Authorization") token: String,
        @Path("reviewId") reviewId: Long,
        @Body modifyReviewRequest: ReviewRequest
    ): Call<ReviewResponse>

    @POST("api/favorites/add")
    fun addFavoriteSpace(
        @Header("Authorization") token: String,
        @Body favoriteSpaceRequest: FavoriteSpaceRequest
    ): Call<Any>

    @GET("api/favorites/get")
    fun getFavoriteSpaceList(
        @Header("Authorization") token: String
    ): Call<FavoriteSpaceResponse>

    @HTTP(method = "DELETE", path = "api/favorites/remove", hasBody = true)
    fun removeFavoriteSpace(
        @Header("Authorization") token: String,
        @Body favoriteSpaceRequest: FavoriteSpaceRequest
    ): Call<Any>

    @GET("api/reservationAddress/get")
    fun getReservationAddressUrl(
        @Query("spaceId") spaceId: Long
    ): Call<List<ReservationResponse>>
}