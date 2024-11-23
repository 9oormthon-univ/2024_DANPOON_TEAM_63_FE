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
import com.example.youthspacefinder.utils
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.KakaoMapSdk
import com.kakao.vectormap.MapLifeCycleCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class YouthSpaceKaKaoMapFragment : Fragment() {

    val binding by lazy { FragmentYouthSpaceKaoKaoMapBinding.inflate(layoutInflater) }
    lateinit var amenities: List<AmenitiesResponse>
    private var kakaoMap: KakaoMap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val spaceName = requireArguments().getString("youth_space_name")
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
            val bundle = bundleOf("address" to spaceAddress)
            findNavController().navigate(R.id.action_youthSpaceKaKaoMapFragment_to_amenitiesDetailFragment, bundle)
        }
        showMapView()
    }

    private fun showMapView() {
        // KakaoMapSDK 초기화!!
        KakaoMapSdk.init(requireContext(), utils.KAKAO_MAP_KEY)
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
}