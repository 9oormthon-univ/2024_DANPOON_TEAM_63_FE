package com.example.youthspacefinder.model

import com.google.gson.annotations.SerializedName

data class ReviewRequest(
    val youthSpaceId: Long,
    val content: String
)

data class ReviewResponse(
    @SerializedName("id") val reviewId: Long,
    val youthSpaceId: Long,
    val username: String,
    val nickname: String,
    val content: String,
    val createAt: String,
    val updateAt: String // 리뷰 수정 시간
)