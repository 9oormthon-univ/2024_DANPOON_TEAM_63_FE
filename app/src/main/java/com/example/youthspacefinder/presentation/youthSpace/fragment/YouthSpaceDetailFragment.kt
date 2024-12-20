package com.example.youthspacefinder.presentation.youthSpace.fragment

import PositionResponse
import FavoriteSpaceRequest
import YouthSpace
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.youthspacefinder.R
import com.example.youthspacefinder.databinding.FragmentYouthSpaceDetailBinding
import com.example.youthspacefinder.network.RetrofitInstance
import com.example.youthspacefinder.Utils
import com.example.youthspacefinder.presentation.authentication.viewmodel.AuthenticationViewModel
import com.example.youthspacefinder.presentation.youthSpace.viewmodel.YouthSpaceFavoritesViewModel
import com.example.youthspacefinder.presentation.youthSpace.viewmodel.YouthSpaceInfoViewModel
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.camera.CameraUpdateFactory
import com.kakao.vectormap.label.LabelOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class YouthSpaceDetailFragment : Fragment() {
    val binding by lazy { FragmentYouthSpaceDetailBinding.inflate(layoutInflater) }
    val youthSpaceInfoViewModel: YouthSpaceInfoViewModel by activityViewModels()
    val authenticationViewModel: AuthenticationViewModel by activityViewModels()
    val youthSpaceFavoritesViewModel: YouthSpaceFavoritesViewModel by activityViewModels()
    var isFavoriteSpace: Boolean? = null
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
        val address = youthSpaceInfoViewModel.spaceAddress
        val spcImage = youthSpaceInfoViewModel.spaceImage
        val spcName = youthSpaceInfoViewModel.spaceName
        val spcTime = youthSpaceInfoViewModel.spcTime
        val telNo = youthSpaceInfoViewModel.telephoneNumber
        RetrofitInstance.networkServiceBackEndLocationController.getLocationXY(
            address = address!!
        ).enqueue(object : Callback<PositionResponse> {
            override fun onResponse(
                call: Call<PositionResponse>,
                response: Response<PositionResponse>
            ) {
                if(response.isSuccessful) {
                    val response = response.body()!!
                    youthSpaceInfoViewModel.spacePositionX = response.positionX
                    youthSpaceInfoViewModel.spacePositionY = response.positionY
                    Log.d("x,y =" ,"${youthSpaceInfoViewModel.spacePositionX}, ${youthSpaceInfoViewModel.spacePositionY}")
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
            if(telNo != "null") tvTelNo.text = telNo else tvTelNo.text = "해당 공간은 연락처를 제공하지 않습니다."
        }
        if(authenticationViewModel.isUserLoggedIn) {
            binding.ivFavorite.visibility = View.VISIBLE
        }
        else {
            binding.ivFavorite.visibility = View.GONE
        }
        isFavoriteSpace = youthSpaceFavoritesViewModel.userFavoriteSpaceIds?.contains(youthSpaceInfoViewModel.spaceId!!.toLong())
        if(isFavoriteSpace == true) binding.ivFavorite.setImageResource(R.drawable.ic_favorite)
        else binding.ivFavorite.setImageResource(R.drawable.ic_unfavorite)
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
                return LatLng.from(youthSpaceInfoViewModel.spacePositionY?.toDouble() ?:0.0, youthSpaceInfoViewModel.spacePositionX?.toDouble() ?:0.0)
            }

            override fun getZoomLevel(): Int {
                return 16
            }
        })
    }

    private fun setMapContent() {
        val latLng = LatLng.from(youthSpaceInfoViewModel.spacePositionY!!.toDouble(), youthSpaceInfoViewModel.spacePositionX!!.toDouble())
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
        binding.btnGoToHomepageSite.setOnClickListener {
            findNavController().navigate(R.id.action_youthSpaceDetailFragment_to_youthSpaceWebViewFragment)
        }
        binding.btnGoToReservationSite.setOnClickListener {
            findNavController().navigate(R.id.action_youthSpaceDetailFragment_to_youthSpaceReservationWebViewFragment)
        }

        binding.ivFavorite.setOnClickListener {
            if(isFavoriteSpace == true) {
                // 삭제
                RetrofitInstance.networkServiceBackEnd.removeFavoriteSpace(
                    token = authenticationViewModel.userToken!!,
                    favoriteSpaceRequest = FavoriteSpaceRequest(youthSpaceInfoViewModel.spaceId!!.toLong())
                ).enqueue(object: Callback<Any> {
                    override fun onResponse(call: Call<Any>, response: Response<Any>) {
                        if(response.isSuccessful) {
                            isFavoriteSpace = false
                            binding.ivFavorite.setImageResource(R.drawable.ic_unfavorite)
                            youthSpaceFavoritesViewModel.userFavoriteSpaceIds?.remove(youthSpaceInfoViewModel.spaceId!!.toLong())
                            val removeBookmarkSpace = youthSpaceFavoritesViewModel.userFavoriteSpaces.find {
                                it.spcId == youthSpaceInfoViewModel.spaceId
                            }
                            youthSpaceFavoritesViewModel.userFavoriteSpaces.remove(removeBookmarkSpace)
                            Toast.makeText(requireContext(), "즐겨찾기에서 삭제 되었습니다!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(requireContext(), "이미 삭제 되었습니다!", Toast.LENGTH_SHORT).show()
                        }
                    }
                    override fun onFailure(call: Call<Any>, t: Throwable) {
                        Toast.makeText(requireContext(), "서버가 불안정합니다!", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                // 추가
                RetrofitInstance.networkServiceBackEnd.addFavoriteSpace(
                    token = authenticationViewModel.userToken!!,
                    favoriteSpaceRequest = FavoriteSpaceRequest(youthSpaceInfoViewModel.spaceId!!.toLong())
                ).enqueue(object: Callback<Any> {
                    override fun onResponse(call: Call<Any>, response: Response<Any>) {
                        if(response.isSuccessful) {
                            isFavoriteSpace = true
                            binding.ivFavorite.setImageResource(R.drawable.ic_favorite)
                            youthSpaceFavoritesViewModel.userFavoriteSpaceIds?.add(youthSpaceInfoViewModel.spaceId!!.toLong())
                            youthSpaceFavoritesViewModel.userFavoriteSpaces?.add(
                                YouthSpace(
                                    address = youthSpaceInfoViewModel.spaceAddress!!,
                                    spcName = youthSpaceInfoViewModel.spaceName!!,
                                    spcTime = youthSpaceInfoViewModel.spcTime!!,
                                    telNo = youthSpaceInfoViewModel.telephoneNumber!!,
                                    spcId = youthSpaceInfoViewModel.spaceId!!,
                                    homepage = youthSpaceInfoViewModel.homepageUrl!!,
                                    officeHours = youthSpaceInfoViewModel.officeHours!!
                                )
                            )
                            Toast.makeText(requireContext(), "즐겨찾기에 등록되었습니다!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(requireContext(), "이미 즐겨찾기에 등록되어있습니다!", Toast.LENGTH_SHORT).show()
                        }
                    }
                    override fun onFailure(call: Call<Any>, t: Throwable) {
                        Toast.makeText(requireContext(), "서버가 불안정합니다!", Toast.LENGTH_SHORT).show()
                    }

                })
            }
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