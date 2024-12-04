package com.example.youthspacefinder.model

data class ReviewResponse(
    val id: Long,
    val youthSpaceId: Long,
    val username: String,
    val nickname: String,
    val content: String,
    val createAt: String,
    val updateAt: String // 리뷰 수정 시간
)
