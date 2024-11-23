package com.example.youthspacefinder.presentation.youthSpace

import PositionResponse
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.youthspacefinder.R
import com.example.youthspacefinder.databinding.FragmentYouthSpaceDetailBinding
import com.example.youthspacefinder.network.RetrofitInstance
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class YouthSpaceDetailFragment : Fragment() {
    val binding by lazy { FragmentYouthSpaceDetailBinding.inflate(layoutInflater) }
    private var kakaoMap: KakaoMap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupListeners()
    }

    private fun setupViews() {
        val spcImage = requireArguments().getInt("spcImage")
        val spcName = requireArguments().getString("spcName")
        val address = requireArguments().getString("address")
        RetrofitInstance.networkServiceAmenities.getLocationXY(address!!).enqueue(object : Callback<PositionResponse> {
            override fun onResponse(
                call: Call<PositionResponse>,
                response: Response<PositionResponse>
            ) {
                if(response.isSuccessful) {
                    val response = response.body()!!
                    val positionX = response.positionX
                    val positionY = response.positionY

                }
            }

            override fun onFailure(call: Call<PositionResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
        val spcTime = requireArguments().getString("spcTime")
//        val operOrgan = requireArguments().getString("operOrgan")
//        val homepage = requireArguments().getString("homepage")
        val telNo = requireArguments().getString("telNo")
//        val openDate = requireArguments().getString("openDate")
//        val applyTarget = requireArguments().getString("applyTarget")
//        val spcCost = requireArguments().getString("spcCost")
//        val foodYn = requireArguments().getString("foodYn")
        binding.apply {
            Glide.with(requireContext()).load(spcImage).into(ivSpcImage)
            tvSpcName.text = spcName
            tvAddress.text = address
            tvSpcTime.text = spcTime
//            tvOperOrgan.text = operOrgan
//            tvHomepage.text = homepage
            tvTelNo.text = telNo
//            tvOpenDate.text = openDate
//            tvApplyTarget.text = applyTarget
//            tvSpcCost.text = spcCost
//            tvFoodYn.text = foodYn
        }
//        KakaoMapSdk.init(requireContext(), utils.KAKAO_MAP_KEY)
        val mapView = MapView(requireContext())
        binding.mapView.addView(mapView)
        showMapView()
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

    private fun setupListeners() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_youthSpaceDetailFragment_to_youthSpaceListFragment)
        }
        binding.btnSearchSurroundingAmenities.setOnClickListener {
            val bundle = bundleOf(
                "youth_space_name" to binding.tvSpcName.text.toString(),
                "youth_space_address" to binding.tvAddress.text.toString()
            )
            findNavController().navigate(R.id.action_youthSpaceDetailFragment_to_youthSpaceKaKaoMapFragment, bundle)
        }
        binding.btnGoToUrl.setOnClickListener {
            val intent = Intent(requireContext(), YouthSpaceWebViewActivity::class.java)
            intent.putExtra("homepage_url", requireArguments().getString("homepage"))
            startActivity(intent)
        }
    }
}