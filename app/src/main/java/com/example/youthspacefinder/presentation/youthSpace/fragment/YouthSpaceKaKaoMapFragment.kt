package com.example.youthspacefinder.presentation.youthSpace.fragment

import AmenitiesResponse
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
import com.example.youthspacefinder.databinding.FragmentYouthSpaceKaoKaoMapBinding
import com.example.youthspacefinder.network.RetrofitInstance
import com.example.youthspacefinder.Utils
import com.example.youthspacefinder.presentation.youthSpace.viewmodel.YouthSpaceViewModel
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
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
    val viewModel: YouthSpaceViewModel by activityViewModels()
    private var kakaoMap: KakaoMap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val spaceName = viewModel.spaceName
        showMapView()
        binding.tvYouthSpaceName.text = spaceName
        // retrofit
        RetrofitInstance.networkServiceBackEnd.getAmenitiesList(viewModel.spaceAddress!!).enqueue(object:
            Callback<List<AmenitiesResponse>> {
            override fun onResponse(
                call: Call<List<AmenitiesResponse>>,
                response: Response<List<AmenitiesResponse>>
            ) {
                if(response.isSuccessful) {
                    viewModel.amenities = response.body()!!
                    Log.d("size",viewModel.amenities!!.size.toString())
                    binding.tvSurroundNumber.text = "추천맛집 ${viewModel.amenities!!.size}건"
                } else {
                    Log.e("API_ERROR", "Error: ${response.errorBody()?.string()}")
                }
            }
            override fun onFailure(call: Call<List<AmenitiesResponse>>, t: Throwable) {
                Log.e("API_FAILURE", "Failure: ${t.message}")
            }
        })
        binding.ivGoToAmenityList.setOnClickListener {
            findNavController().navigate(R.id.action_youthSpaceKaKaoMapFragment_to_amenitiesKaKaoMapFragment)
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
                return LatLng.from(viewModel.spacePositionY?.toDouble() ?:0.0, viewModel.spacePositionX?.toDouble() ?:0.0)
            }
        })
    }

    private fun setMapContent() {
        val latLng = LatLng.from(viewModel.spacePositionY!!.toDouble(), viewModel.spacePositionX!!.toDouble())
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