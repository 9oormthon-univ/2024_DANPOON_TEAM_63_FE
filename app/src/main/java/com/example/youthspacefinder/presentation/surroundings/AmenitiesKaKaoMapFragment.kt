package com.example.youthspacefinder.presentation.surroundings

import AmenitiesResponse
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.youthspacefinder.databinding.FragmentAmenitiesKaKaoMapBinding
import com.example.youthspacefinder.utils
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.KakaoMapSdk
import com.kakao.vectormap.MapLifeCycleCallback
import java.lang.Exception

class AmenitiesKaKaoMapFragment : Fragment() {

    private val binding by lazy { FragmentAmenitiesKaKaoMapBinding.inflate(layoutInflater) }
    private var kakaoMap: KakaoMap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showMapView()
        val amenities = requireArguments().getParcelableArrayList<AmenitiesResponse>("amenities")
//        amenities!!.forEach {
//            showLocationOnMap(it.positionX.toDouble(), it.positionY.toDouble())
//        }
    }

    private fun showMapView() {
        // KakaoMapSDK 초기화!!
//        KakaoMapSdk.init(requireContext(), utils.KAKAO_MAP_KEY)
        binding.mapView.start(object : MapLifeCycleCallback() {
            override fun onMapDestroy() {
                // 지도 API가 정상적으로 종료될 때 호출
                Log.d("KakaoMap", "onMapDestroy")
            }

            override fun onMapError(p0: Exception?) {
                // 인증 실패 및 지도 사용 중 에러가 발생할 때 호출
                Log.e("KakaoMap", "${p0?.message}")
                p0?.printStackTrace()
            }

        }, object : KakaoMapReadyCallback() {
            override fun onMapReady(kakaomap: KakaoMap) {
                kakaoMap = kakaomap
            }
        })
    }

    private fun showLocationOnMap(x: Double, y: Double) {

    }

}