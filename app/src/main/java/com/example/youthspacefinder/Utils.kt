package com.example.youthspacefinder

import com.kakao.vectormap.label.LabelStyle
import com.kakao.vectormap.label.LabelStyles
import com.kakao.vectormap.label.LabelTextStyle

object Utils {
    const val YOUTH_OPEN_API_KEY = BuildConfig.YOUTH_OPEN_API_KEY
    const val KAKAO_MAP_KEY = BuildConfig.KAKAO_MAP_KEY
    val regionCode = mapOf(
        "서울" to "003002001",
        "부산" to "003002002",
        "대구" to "003002003",
        "인천" to "003002004",
        "광주" to "003002005",
        "대전" to "003002006",
        "울산" to "003002007",
        "경기" to "003002008",
        "강원" to "003002009",
        "충북" to "003002010",
        "충남" to "003002011",
        "전북" to "003002012",
        "전남" to "003002013",
        "경북" to "003002014",
        "경남" to "003002015",
        "제주" to "003002016",
        "세종" to "003002017"
    )
    val youthSpaceImageList = listOf(
        R.drawable.image_space_1,
        R.drawable.image_space_2,
        R.drawable.image_space_3,
        R.drawable.image_space_4,
        R.drawable.image_space_5,
        R.drawable.image_space_6,
        R.drawable.image_space_7,
        R.drawable.image_space_8,
        R.drawable.image_space_9,
        R.drawable.image_space_10,
        R.drawable.image_space_11,
        R.drawable.image_space_12,
        R.drawable.image_space_13,
        R.drawable.image_space_14,
        R.drawable.image_space_15,
        R.drawable.image_space_16,
        R.drawable.image_space_17,
        R.drawable.image_space_18,
        R.drawable.image_space_19,
        R.drawable.image_space_20
    )

    fun setPinStyle(isSelected: Boolean): LabelStyle {
        if (isSelected) {
            // 선택된 핀
            return LabelStyle.from(
                R.drawable.ic_pin_selected
            )
            // .setTextStyles(20, R.color.black)
        }
        return LabelStyle.from(
            // 기본 핀
            R.drawable.ic_pin_default
        )
    }
}