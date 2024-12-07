package com.example.youthspacefinder.presentation.youthSpace.viewmodel

import AmenitiesResponse
import androidx.lifecycle.ViewModel

class YouthSpaceInfoViewModel: ViewModel() {
    var spaceImage: Int? = null
    var spaceName: String? = null
    var spaceId: String? = null
    var spaceAddress: String? = null
    var spacePositionX: String? = null
    var spacePositionY: String? = null
    var spcTime: String? = null // 자세한 정보
    var officeHours: String? = null // 간략한 정보
    var operateOrgan: String? = null
    var homepageUrl: String? = null
    var telephoneNumber: String? = null
    var spaceOpenDate: String? = null
    var applyTarget: String? = null
    var spaceCost: String? = null
    var foodYn: String? = null
    var amenities: List<AmenitiesResponse> ?= null
}