package com.example.youthspacefinder.presentation.youthSpace.viewmodel

import YouthSpace
import androidx.lifecycle.ViewModel

class YouthSpaceFavoritesViewModel: ViewModel() {
    var userFavoriteSpaceIds = arrayListOf<Long>()
    var userFavoriteSpaces = arrayListOf<YouthSpace>()
}