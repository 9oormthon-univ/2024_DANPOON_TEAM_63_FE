package com.example.youthspacefinder

import android.app.Application
import com.kakao.vectormap.KakaoMapSdk

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
//        KakaoSdk.init(this, utils.KAKAO_MAP_KEY)
        KakaoMapSdk.init(this, Utils.KAKAO_MAP_KEY)
    }
}