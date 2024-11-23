package com.example.youthspacefinder.presentation.surroundings

import AmenitiesResponse
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.youthspacefinder.R
import com.example.youthspacefinder.databinding.FragmentAmenitiesDetailBinding
import com.example.youthspacefinder.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AmenitiesDetailFragment : Fragment() {

    val binding by lazy { FragmentAmenitiesDetailBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val address = requireArguments().getString("address")
        RetrofitInstance.networkServiceAmenities.getAmenitiesList(address!!).enqueue(object:
            Callback<List<AmenitiesResponse>> {
            override fun onResponse(
                call: Call<List<AmenitiesResponse>>,
                response: Response<List<AmenitiesResponse>>
            ) {
                if(response.isSuccessful) {
                    val response = response.body()!!
                    binding.recylerview.adapter = AmenitiesListDetailAdapter(response, requireContext())
                } else {
                    Log.e("API_ERROR", "Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<List<AmenitiesResponse>>, t: Throwable) {
                Log.e("API_FAILURE", "Failure: ${t.message}")
            }
        })
    }
}