package com.example.youthspacefinder

import com.kakao.vectormap.label.LabelStyle

object Utils {
    const val YOUTH_OPEN_API_KEY = BuildConfig.YOUTH_OPEN_API_KEY
    const val KAKAO_MAP_KEY = BuildConfig.KAKAO_MAP_KEY
    val regionCode = mapOf(
        "서울" to mapOf(
            "code" to "003002001", // 9자리
            "districts" to mapOf(
                "종로구" to "003002001001", // 12자리
                "중구" to "003002001002",
                "용산구" to "003002001003",
                "성동구" to "003002001004",
                "광진구" to "003002001005",
                "동대문구" to "003002001006",
                "중랑구" to "003002001007",
                "성북구" to "003002001008",
                "강북구" to "003002001009",
                "도봉구" to "003002001010",
                "노원구" to "003002001011",
                "은평구" to "003002001012",
                "서대문구" to "003002001013",
                "마포구" to "003002001014",
                "양천구" to "003002001015",
                "강서구" to "003002001016",
                "구로구" to "003002001017",
                "금천구" to "003002001018",
                "영등포구" to "003002001019",
                "동작구" to "003002001020",
                "관악구" to "003002001021",
                "서초구" to "003002001022",
                "강남구" to "003002001023",
                "송파구" to "003002001024",
                "강동구" to "003002001025",
                "동부기술교육원" to "003002001026"
            )
        ),
        "부산" to mapOf(
            "code" to "003002002",
            "districts" to mapOf(
                "중구" to "003002002001",
                "서구" to "003002002002",
                "동구" to "003002002003",
                "영도구" to "003002002004",
                "부산진구" to "003002002005",
                "동래구" to "003002002006",
                "남구" to "003002002007",
                "북구" to "003002002008",
                "해운대구" to "003002002009",
                "사하구" to "003002002010",
                "금정구" to "003002002011",
                "강서구" to "003002002012",
                "연제구" to "003002002013",
                "수영구" to "003002002014",
                "사상구" to "003002002015",
                "기장군" to "003002002016"
            )
        ),
        "대구" to mapOf(
            "code" to "003002003",
            "districts" to mapOf(
                "중구" to "003002003001",
                "동구" to "003002003002",
                "서구" to "003002003003",
                "남구" to "003002003004",
                "북구" to "003002003005",
                "수성구" to "003002003006",
                "달서구" to "003002003007",
                "달성군" to "003002003008",
                "군위군" to "003002003009"
            )
        ),
        "인천" to mapOf(
            "code" to "003002004",
            "districts" to mapOf(
                "중구" to "003002004001",
                "동구" to "003002004002",
                "미추홀구" to "003002004004",
                "연수구" to "003002004005",
                "남동구" to "003002004006",
                "부평구" to "003002004007",
                "계양구" to "003002004008",
                "서구" to "003002004009",
                "강화군" to "003002004010",
                "옹진군" to "003002004011"
            )
        ),
        "광주" to mapOf(
            "code" to "003002005",
            "districts" to mapOf(
                "동구" to "003002005001",
                "서구" to "003002005002",
                "남구" to "003002005003",
                "북구" to "003002005004",
                "광산" to "003002005005"
            )
        ),
        "대전" to mapOf(
            "code" to "003002006",
            "districts" to mapOf(
                "동구" to "003002006001",
                "중구" to "003002006002",
                "서구" to "003002006003",
                "유성구" to "003002006004",
                "대덕구" to "003002006005"
            )
        ),
        "울산" to mapOf(
            "code" to "003002007",
            "districts" to mapOf(
                "중구" to "003002007001",
                "남구" to "003002007002",
                "동구" to "003002007003",
                "북구" to "003002007004",
                "울주군" to "003002007005"
            )
        ),
        "경기" to mapOf(
            "code" to "003002008",
            "districts" to mapOf(
                "수원시" to "003002008001",
                "성남시" to "003002008002",
                "의정부시" to "003002008003",
                "안양시" to "003002008004",
                "부천시" to "003002008005",
                "광명시" to "003002008006",
                "평택시" to "003002008007",
                "동두천시" to "003002008008",
                "안산시" to "003002008009",
                "고양시" to "003002008010",
                "과천시" to "003002008011",
                "구리시" to "003002008012",
                "남양주시" to "003002008013",
                "오산시" to "003002008014",
                "시흥시" to "003002008015",
                "군포시" to "003002008016",
                "의왕시" to "003002008017",
                "하남시" to "003002008018",
                "용인시" to "003002008019",
                "파주시" to "003002008020",
                "이천시" to "003002008021",
                "안성시" to "003002008022",
                "김포시" to "003002008023",
                "화성시" to "003002008024",
                "광주시" to "003002008025",
                "양주시" to "003002008026",
                "포천시" to "003002008027",
                "여주시" to "003002008028",
                "연천군" to "003002008031",
                "가평군" to "003002008033",
                "양평군" to "003002008034"
            )
        ),
        "강원" to mapOf(
            "code" to "003002009",
            "districts" to mapOf(
                "춘천시" to "003002009001",
                "원주시" to "003002009002",
                "강릉시" to "003002009003",
                "동해시" to "003002009004",
                "태백시" to "003002009005",
                "속초시" to "003002009006",
                "삼척시" to "003002009007",
                "홍천군" to "003002009008",
                "횡성군" to "003002009009",
                "영월군" to "003002009010",
                "평창군" to "003002009011",
                "정선군" to "003002009012",
                "철원군" to "003002009013",
                "화천군" to "003002009014",
                "양구군" to "003002009015",
                "인제군" to "003002009016",
                "고성군" to "003002009017",
                "양양군" to "003002009018"
            )
        ),
        "충북" to mapOf(
            "code" to "003002010",
            "districts" to mapOf(
                "청주시" to "003002010001",
                "충주시" to "003002010002",
                "제천시" to "003002010003",
                "보은군" to "003002010005",
                "옥천군" to "003002010006",
                "영동군" to "003002010007",
                "증평군" to "003002010008",
                "진천군" to "003002010009",
                "괴산군" to "003002010010",
                "음성군" to "003002010011",
                "단양군" to "003002010012"
            )
        ),
        "충남" to mapOf(
            "code" to "003002011",
            "districts" to mapOf(
                "천안시" to "003002011001",
                "공주시" to "003002011002",
                "보령시" to "003002011003",
                "아산시" to "003002011004",
                "서산시" to "003002011005",
                "논산시" to "003002011006",
                "계룡시" to "003002011007",
                "당진시" to "003002011008",
                "금산군" to "003002011009",
                "부여군" to "003002011011",
                "서천군" to "003002011012",
                "청양군" to "003002011013",
                "홍성군" to "003002011014",
                "예산군" to "003002011015",
                "태안군" to "003002011016"
            )
        ),
        "전북" to mapOf(
            "code" to "003002012",
            "districts" to mapOf(
                "전주시" to "003002012001",
                "군산시" to "003002012002",
                "익산시" to "003002012003",
                "정읍시" to "003002012004",
                "남원시" to "003002012005",
                "김제시" to "003002012006",
                "완주군" to "003002012007",
                "진안군" to "003002012008",
                "무주군" to "003002012009",
                "장수군" to "003002012010",
                "임실군" to "003002012011",
                "순창군" to "003002012012",
                "고창군" to "003002012013",
                "부안군" to "003002012014"
            )
        ),
        "전남" to mapOf(
            "code" to "003002013",
            "districts" to mapOf(
                "목포시" to "003002013001",
                "여수시" to "003002013002",
                "순천시" to "003002013003",
                "나주시" to "003002013004",
                "광양시" to "003002013005",
                "담양군" to "003002013006",
                "곡성군" to "003002013007",
                "구례군" to "003002013008",
                "고흥군" to "003002013009",
                "보성군" to "003002013010",
                "화순군" to "003002013011",
                "장흥군" to "003002013012",
                "강진군" to "003002013013",
                "해남군" to "003002013014",
                "영암군" to "003002013015",
                "무안군" to "003002013016",
                "함평군" to "003002013017",
                "영광군" to "003002013018",
                "장성군" to "003002013019",
                "완도군" to "003002013020",
                "진도군" to "003002013021",
                "신안군" to "003002013022"
            )
        ),
        "경북" to mapOf(
            "code" to "003002014",
            "districts" to mapOf(
                "포항시" to "003002014001",
                "경주시" to "003002014002",
                "김천시" to "003002014003",
                "안동시" to "003002014004",
                "구미시" to "003002014005",
                "영주시" to "003002014006",
                "영천시" to "003002014007",
                "상주시" to "003002014008",
                "문경시" to "003002014009",
                "경산시" to "003002014010",
                "의성군" to "003002014012",
                "청송군" to "003002014013",
                "영양군" to "003002014014",
                "영덕군" to "003002014015",
                "청도군" to "003002014016",
                "고령군" to "003002014017",
                "성주군" to "003002014018",
                "칠곡군" to "003002014019",
                "예천군" to "003002014020",
                "봉화군" to "003002014021",
                "울진군" to "003002014022",
                "울릉군" to "003002014023"
            )
        ),
        "경남" to mapOf(
            "code" to "003002015",
            "districts" to mapOf(
                "창원시" to "003002015001",
                "진주시" to "003002015003",
                "통영시" to "003002015005",
                "사천시" to "003002015006",
                "김해시" to "003002015007",
                "밀양시" to "003002015008",
                "거제시" to "003002015009",
                "양산시" to "003002015010",
                "의령군" to "003002015011",
                "함안군" to "003002015012",
                "창녕군" to "003002015013",
                "고성군" to "003002015014",
                "남해군" to "003002015015",
                "하동군" to "003002015016",
                "산청군" to "003002015017",
                "함양군" to "003002015018",
                "거창군" to "003002015019",
                "합천군" to "003002015020"
            )
        ),
        "제주" to mapOf(
            "code" to "003002016",
            "districts" to mapOf(
                "제주시" to "003002016001",
                "서귀포시" to "003002016002"
            )
        ),
        "세종" to mapOf(
            "code" to "003002017",
            "districts" to mapOf(
                "세종" to "003002017001"
            )
        )
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
        R.drawable.image_space_20,
        R.drawable.image_space_21,
        R.drawable.image_space_22,
        R.drawable.image_space_23,
        R.drawable.image_space_24,
        R.drawable.image_space_25,
        R.drawable.image_space_26,
        R.drawable.image_space_27,
        R.drawable.image_space_28,
        R.drawable.image_space_29,
        R.drawable.image_space_30,
        R.drawable.image_space_31,
        R.drawable.image_space_32,
        R.drawable.image_space_33,
        R.drawable.image_space_34,
        R.drawable.image_space_35,
        R.drawable.image_space_36,
        R.drawable.image_space_37,
        R.drawable.image_space_38,
        R.drawable.image_space_39,
        R.drawable.image_space_40
    )

    val TAG_RESTAURANT = "FD6"
    val TAG_CAFE = "CE7"

    fun setPinStyle(isSelected: Boolean): LabelStyle {
        if (isSelected) {
            // 선택된 핀
            return LabelStyle.from(
                R.drawable.ic_pin_selected
            ).setTextStyles(20, R.color.black)
        }
        return LabelStyle.from(
            // 기본 핀
            R.drawable.ic_pin_default
        )
    }
}