package com.example.youthspacefinder.presentation.youthSpace.viewmodel

import YouthSpace
import androidx.lifecycle.ViewModel

class YouthSpaceFavoritesViewModel: ViewModel() {
    var userFavoriteSpaceIds: ArrayList<Long> ?= null
    var userFavoriteSpaces: ArrayList<YouthSpace> ?= null
}