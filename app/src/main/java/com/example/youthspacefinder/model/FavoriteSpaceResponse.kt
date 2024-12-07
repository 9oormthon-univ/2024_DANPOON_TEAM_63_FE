package com.example.youthspacefinder.model

import com.google.gson.annotations.SerializedName

data class FavoriteSpaceResponse(
    val username: String,
    @SerializedName("favoriteSpaces") val favoriteSpaceIds: ArrayList<Long>
)
