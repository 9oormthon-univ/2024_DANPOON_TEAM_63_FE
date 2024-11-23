package com.example.youthspacefinder.presentation.youthSpace

import AmenitiesResponse
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.youthspacefinder.R
import com.example.youthspacefinder.databinding.FragmentYouthSpaceKaoKaoMapBinding
import com.example.youthspacefinder.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class YouthSpaceKaKaoMapFragment : Fragment() {

    val binding by lazy { FragmentYouthSpaceKaoKaoMapBinding.inflate(layoutInflater) }
    lateinit var amenities: List<AmenitiesResponse>

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
            val bundle = Bundle().apply { putParcelableArrayList("amenities", ArrayList(amenities)) }
            findNavController().navigate(R.id.action_youthSpaceKaKaoMapFragment_to_amenitiesKaKaoMapFragment, bundle)
        }
    }
}