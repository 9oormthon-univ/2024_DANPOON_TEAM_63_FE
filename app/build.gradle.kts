import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("org.jetbrains.kotlin.plugin.serialization")
    id("kotlin-parcelize")
    kotlin("kapt")
//    id("org.jetbrains.kotlin.android")
}

val properties = Properties().apply {
    load(FileInputStream(rootProject.file("local.properties")))
}

android {
    namespace = "com.example.youthspacefinder"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.youthspacefinder"
        minSdk = 33
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "KAKAO_MAP_KEY", properties.getProperty("KAKAO_MAP_KEY"))
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.retrofit2.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.kotlinx.serialization.core)
    implementation(libs.converter.simplexml)
    implementation(libs.squareup.converter.scalars)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.glide)
    implementation(libs.jetbrains.kotlinx.serialization.core.v151)
    // 외부 라이브러리
    implementation("com.tickaroo.tikxml:annotation:0.8.13")
    implementation("com.tickaroo.tikxml:core:0.8.13")
    implementation("com.tickaroo.tikxml:retrofit-converter:0.8.13")
    // circle image view
    implementation("de.hdodenhof:circleimageview:3.1.0")
    kapt("com.tickaroo.tikxml:processor:0.8.13")
    // kakao map api
    implementation("com.kakao.maps.open:android:2.12.8")
    // 이상하면 지우기
//    implementation("net.daum.android.map:map-sdk:1.4.0") // 최신 버전 확인 필요

    implementation("com.kakao.sdk:v2-all:2.20.1") // 전체 모듈 설치, 2.11.0 버전부터 지원
    implementation("com.kakao.sdk:v2-user:2.20.1") // 카카오 로그인 API 모듈
    implementation("com.kakao.sdk:v2-share:2.20.1") // 카카오톡 공유 API 모듈
    implementation("com.kakao.sdk:v2-talk:2.20.1")// 카카오톡 채널, 카카오톡 소셜, 카카오톡 메시지 API 모듈
    implementation("com.kakao.sdk:v2-friend:2.20.1") // 피커 API 모듈
    implementation("com.kakao.sdk:v2-navi:2.20.1")// 카카오내비 API 모듈
    implementation("com.kakao.sdk:v2-cert:2.20.1")// 카카오톡 인증 서비스 API 모듈
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}