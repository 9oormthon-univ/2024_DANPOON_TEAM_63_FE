package com.example.youthspacefinder.presentation.surroundings.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.youthspacefinder.R
import com.example.youthspacefinder.Utils
import com.example.youthspacefinder.databinding.FragmentAmenitiesKaKaoMapBinding
import com.example.youthspacefinder.presentation.youthSpace.viewmodel.YouthSpaceInfoViewModel
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.camera.CameraUpdateFactory
import com.kakao.vectormap.label.Label
import com.kakao.vectormap.label.LabelLayer
import com.kakao.vectormap.label.LabelOptions

class AmenitiesKaKaoMapFragment : Fragment() {

    private val binding by lazy { FragmentAmenitiesKaKaoMapBinding.inflate(layoutInflater) }
    private var kakaoMap: KakaoMap? = null
    val youthSpaceInfoViewModel: YouthSpaceInfoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showMapView()
    }

    private fun showMapView() {
//        KakaoMapSdk.init(requireContext(), Utils.KAKAO_MAP_KEY)
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
                youthSpaceInfoViewModel.amenities!!.forEach {
                    setAmenitiesPosition(it.positionX, it.positionY, it.placeUrl)
                }
                setLabelClickListener()
            }
        })
    }

    private fun setYouthSpacePosition() {
        val latLng = LatLng.from(youthSpaceInfoViewModel.spacePositionY!!.toDouble(), youthSpaceInfoViewModel.spacePositionX!!.toDouble())
        kakaoMap!!.moveCamera(CameraUpdateFactory.newCenterPosition(latLng, 17))
        kakaoMap!!.labelManager!!.layer!!.addLabel(LabelOptions.from(latLng).setStyles(Utils.setPinStyle(false)))
    }

    private fun setAmenitiesPosition(positionX: String, positionY: String, amenityUrl: String) {
        val latLng = LatLng.from(positionY.toDouble(), positionX.toDouble())
        val labelOption = LabelOptions.from(latLng).setStyles(Utils.setPinStyle(true))
        labelOption.tag = amenityUrl
        kakaoMap!!.labelManager!!.layer!!.addLabel(labelOption)
    }

    private fun setLabelClickListener() {
        kakaoMap!!.setOnLabelClickListener(object : KakaoMap.OnLabelClickListener {
            override fun onLabelClicked(kakaoMap: KakaoMap?, labelLayer: LabelLayer?, label: Label?) {
                val url = label?.tag
                Log.d("url", url!!.toString())
                val bundle = bundleOf("amenity_url" to url.toString())
                findNavController().navigate(R.id.action_amenitiesKaKaoMapFragment_to_amenityWebViewFragment, bundle)
            }
        })
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