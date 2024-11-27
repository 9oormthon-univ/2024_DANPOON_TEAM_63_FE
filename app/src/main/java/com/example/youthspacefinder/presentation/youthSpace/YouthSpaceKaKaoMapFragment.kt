package com.example.youthspacefinder.presentation.youthSpace

import AmenitiesResponse
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.youthspacefinder.R
import com.example.youthspacefinder.databinding.FragmentYouthSpaceKaoKaoMapBinding
import com.example.youthspacefinder.network.RetrofitInstance
import com.example.youthspacefinder.Utils
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.KakaoMapSdk
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.camera.CameraUpdateFactory
import com.kakao.vectormap.label.LabelOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class YouthSpaceKaKaoMapFragment : Fragment() {

    val binding by lazy { FragmentYouthSpaceKaoKaoMapBinding.inflate(layoutInflater) }
    lateinit var amenities: List<AmenitiesResponse>
    private var kakaoMap: KakaoMap? = null
    private var positionX: String ?= null
    private var positionY: String ?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val spaceName = requireArguments().getString("youth_space_name")
        positionX = requireArguments().getString("youth_space_position_x")
        positionY = requireArguments().getString("youth_space_position_y")
        showMapView()
        binding.tvYouthSpaceName.text = spaceName
        // retrofit
        val spaceAddress = requireArguments().getString("youth_space_address")
        RetrofitInstance.networkServiceAmenities.getAmenitiesList(spaceAddress!!).enqueue(object:
            Callback<List<AmenitiesResponse>> {
            override fun onResponse(
                call: Call<List<AmenitiesResponse>>,
                response: Response<List<AmenitiesResponse>>
            ) {
                if(response.isSuccessful) {
                    amenities = response.body()!!
                    Log.d("size",amenities.size.toString())
                    binding.tvSurroundNumber.text = "추천맛집 ${amenities.size}건"
                } else {
                    Log.e("API_ERROR", "Error: ${response.errorBody()?.string()}")
                }
            }
            override fun onFailure(call: Call<List<AmenitiesResponse>>, t: Throwable) {
                Log.e("API_FAILURE", "Failure: ${t.message}")
            }
        })
        binding.ivGoToAmenityList.setOnClickListener {
            val bundle = bundleOf(
                "amenities" to amenities,
                "youth_space_position_x" to positionX, // 화면 넘겨줄때마다 데이터 넘겨주기 번거로움 → viewmodel 로 refactoring 하기
                "youth_space_position_y" to positionY
            )
            findNavController().navigate(R.id.action_youthSpaceKaKaoMapFragment_to_amenitiesKaKaoMapFragment, bundle)
        }
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
                setMapContent()
            }

            override fun getPosition(): LatLng {
                return LatLng.from(positionY?.toDouble() ?:0.0, positionX?.toDouble() ?:0.0)
            }
        })
    }

    private fun setMapContent() {
        val latLng = LatLng.from(positionY!!.toDouble(), positionX!!.toDouble())
        kakaoMap!!.moveCamera(CameraUpdateFactory.newCenterPosition(latLng, 16))
        kakaoMap!!.labelManager!!.layer!!.addLabel(LabelOptions.from(latLng).setStyles(Utils.setPinStyle(false)))
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