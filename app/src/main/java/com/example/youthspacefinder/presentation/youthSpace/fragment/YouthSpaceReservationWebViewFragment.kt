package com.example.youthspacefinder.presentation.youthSpace.fragment

import ReservationResponse
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import com.example.youthspacefinder.databinding.FragmentYouthSpaceReservationWebViewBinding
import com.example.youthspacefinder.network.RetrofitInstance
import com.example.youthspacefinder.presentation.MainActivity
import com.example.youthspacefinder.presentation.youthSpace.viewmodel.YouthSpaceInfoViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class YouthSpaceReservationWebViewFragment : Fragment() {

    val binding by lazy { FragmentYouthSpaceReservationWebViewBinding.inflate(layoutInflater) }
    val youthSpaceInfoViewModel: YouthSpaceInfoViewModel by activityViewModels()
    var reservationUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        RetrofitInstance.networkServiceBackEnd.getReservationAddressUrl(spaceId = youthSpaceInfoViewModel.spaceId?.toLong()!!)
            .enqueue(object: Callback<List<ReservationResponse>> {
                override fun onResponse(
                    call: Call<List<ReservationResponse>>,
                    response: Response<List<ReservationResponse>>
                ) {
                    if(response.isSuccessful) {
                        val response = response.body()!!
                        reservationUrl = response.first().reservationAddress
                        binding.webView.loadUrl(reservationUrl!!)
                        goBack()
                    }
                }
                override fun onFailure(call: Call<List<ReservationResponse>>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
    }

    private fun goBack() {
        binding.webView.setOnKeyListener { v, keyCode, event ->
            // 이 필터는 액션이 ACTION_DOWN인지 확인합니다.
            if (event.action != KeyEvent.ACTION_DOWN) {
                return@setOnKeyListener true
            }
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (binding.webView.canGoBack()) {
                    binding.webView.goBack()
                } else {
                    (activity as? MainActivity)?.onBackPressed()
                }
                return@setOnKeyListener true
            }
            false
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (binding.webView.canGoBack()) {
                binding.webView.goBack()
            } else {
                Log.d("can't go back", "true")
                isEnabled = false
                requireActivity().onBackPressed()
            }
        }
    }

}