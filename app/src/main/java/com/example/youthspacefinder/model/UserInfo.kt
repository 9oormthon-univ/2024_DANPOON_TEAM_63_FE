package com.example.youthspacefinder.model

data class UserReviewInfo(
    val userImage: String = "",
    val nickname: String,
    val dateTime: String,
    val content: String
)

data class UserTokenResponse(
    val token: String
)
