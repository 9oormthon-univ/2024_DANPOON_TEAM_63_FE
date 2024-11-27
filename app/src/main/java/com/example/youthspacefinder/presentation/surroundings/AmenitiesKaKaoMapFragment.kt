package com.example.youthspacefinder.presentation.surroundings

import AmenitiesResponse
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.youthspacefinder.databinding.FragmentAmenitiesKaKaoMapBinding
import com.example.youthspacefinder.Utils
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.KakaoMapSdk
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.camera.CameraUpdateFactory
import com.kakao.vectormap.label.LabelOptions
import java.lang.Exception

class AmenitiesKaKaoMapFragment : Fragment() {

    private val binding by lazy { FragmentAmenitiesKaKaoMapBinding.inflate(layoutInflater) }
    private var kakaoMap: KakaoMap? = null
    private var youthSpacePositionX: String ?= null
    private var youthSpacePositionY: String ?= null
    private var amenities: ArrayList<AmenitiesResponse> ?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        amenities = requireArguments().getParcelableArrayList("amenities") // deprecated → 나중에 refactoring 하기
        youthSpacePositionX = requireArguments().getString("youth_space_position_x")
        youthSpacePositionY = requireArguments().getString("youth_space_position_y")

        showMapView()
    }

    private fun showMapView() {
        KakaoMapSdk.init(requireContext(), Utils.KAKAO_MAP_KEY)
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
                setYouthSpacePosition()
                amenities!!.forEach {
                    Log.d("x,y = ","${it.positionX}, ${it.positionY}")
                    setAmenitiesPosition(it.positionX, it.positionY)
                }
            }
        })
    }

    private fun setYouthSpacePosition() {
        val latLng = LatLng.from(youthSpacePositionY!!.toDouble(), youthSpacePositionX!!.toDouble())
        kakaoMap!!.moveCamera(CameraUpdateFactory.newCenterPosition(latLng, 16))
        kakaoMap!!.labelManager!!.layer!!.addLabel(LabelOptions.from(latLng).setStyles(Utils.setPinStyle(false)))
    }

    private fun setAmenitiesPosition(positionX: String, positionY: String) {
        val latLng = LatLng.from(positionY.toDouble(), positionX.toDouble())
        kakaoMap!!.labelManager!!.layer!!.addLabel(LabelOptions.from(latLng).setStyles(Utils.setPinStyle(true)))
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.resume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.pause()
    }
}