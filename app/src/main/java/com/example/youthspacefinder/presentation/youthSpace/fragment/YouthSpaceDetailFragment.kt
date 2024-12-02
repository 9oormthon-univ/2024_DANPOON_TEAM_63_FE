package com.example.youthspacefinder.presentation.youthSpace.fragment

import PositionResponse
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.youthspacefinder.R
import com.example.youthspacefinder.databinding.FragmentYouthSpaceDetailBinding
import com.example.youthspacefinder.network.RetrofitInstance
import com.example.youthspacefinder.Utils
import com.example.youthspacefinder.presentation.youthSpace.viewmodel.YouthSpaceViewModel
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

class YouthSpaceDetailFragment : Fragment() {
    val binding by lazy { FragmentYouthSpaceDetailBinding.inflate(layoutInflater) }
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
        setupViews()
        setupListeners()
    }

    private fun setupViews() {
        val address = viewModel.spaceAddress
        val spcImage = viewModel.spaceImage
        val spcName = viewModel.spaceName
        val spcTime = viewModel.spaceTime
        val telNo = viewModel.telephoneNumber
        RetrofitInstance.networkServiceBackEnd.getLocationXY(address!!).enqueue(object : Callback<PositionResponse> {
            override fun onResponse(
                call: Call<PositionResponse>,
                response: Response<PositionResponse>
            ) {
                if(response.isSuccessful) {
                    val response = response.body()!!
                    viewModel.spacePositionX = response.positionX
                    viewModel.spacePositionY = response.positionY
//                    Log.d("x,y =" ,"$positionX, $positionY")
                    showMapView()
                }
            }

            override fun onFailure(call: Call<PositionResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
        binding.apply {
            Glide.with(requireContext()).load(spcImage).into(ivSpcImage)
            tvSpcName.text = spcName
            tvAddress.text = address
            tvSpcTime.text = spcTime
            tvTelNo.text = telNo
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
                Log.d("KakaoMap", "onMapReady")
                kakaoMap = kakaomap
                setMapContent()
            }

            override fun getPosition(): LatLng {
                return LatLng.from(viewModel.spacePositionY?.toDouble() ?:0.0, viewModel.spacePositionX?.toDouble() ?:0.0)
            }

            override fun getZoomLevel(): Int {
                return 16
            }
        })
    }

    private fun setMapContent() {
        val latLng = LatLng.from(viewModel.spacePositionY!!.toDouble(), viewModel.spacePositionX!!.toDouble())
        kakaoMap!!.moveCamera(CameraUpdateFactory.newCenterPosition(latLng, 16))
        kakaoMap!!.labelManager!!.layer!!.addLabel(LabelOptions.from(latLng).setStyles(Utils.setPinStyle(false)))
    }

    private fun setupListeners() {
        binding.btnUserReviews.setOnClickListener {
            findNavController().navigate(R.id.action_youthSpaceDetailFragment_to_youthSpaceReviewFragment)
        }
        binding.btnSearchSurroundingAmenities.setOnClickListener {
            findNavController().navigate(R.id.action_youthSpaceDetailFragment_to_youthSpaceKaKaoMapFragment)
        }
        binding.btnGoToUrl.setOnClickListener {
            findNavController().navigate(R.id.action_youthSpaceDetailFragment_to_youthSpaceWebViewFragment)
        }
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