package com.example.youthspacefinder.model

data class UserReviewInfo(
    val userImage: String = "",
    val nickname: String,
    val dateTime: String,
    val content: String
)

data class RegisterUserInfo(
    val username: String,
    val nickname: String,
    val password: String,
    val email: String
)

data class LoginUserInfo(
    val username: String,
    val password: String
)

data class UserTokenResponse(
    val token: String
)

data class UserNicknameRequest(
    val newNickname: String
)

data class UserPasswordRequest(
    val currentPassword: String,
    val newPassword: String,
    val confirmNewPassword: String
)

data class ReviewRequest(
    val youthSpaceId: Long,
    val content: String
)