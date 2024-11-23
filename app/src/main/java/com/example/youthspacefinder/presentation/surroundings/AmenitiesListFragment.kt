package com.example.youthspacefinder.presentation.surroundings

import SpacesInfoResponse
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.youthspacefinder.R
import com.example.youthspacefinder.databinding.FragmentAmenitiesListBinding
import com.example.youthspacefinder.databinding.FragmentYouthSpaceListBinding
import com.example.youthspacefinder.network.RetrofitInstance
import com.example.youthspacefinder.presentation.youthSpace.YouthSpaceListAdapter
import com.example.youthspacefinder.utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AmenitiesListFragment : Fragment() {
    val binding by lazy { FragmentAmenitiesListBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        networking()
    }

    private fun networking() {
        RetrofitInstance.networkService.getYouthSpaceList(utils.apiKey, utils.regionCode["서울"]!!)
            .enqueue(object : Callback<SpacesInfoResponse> {
                override fun onResponse(
                    call: Call<SpacesInfoResponse>,
                    response: Response<SpacesInfoResponse>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()!!.youthSpaces
                        binding.recyclerview.adapter = AmenitiesListAdapter(data, requireContext())
                    } else {
                        Log.e("API_ERROR", "Error: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<SpacesInfoResponse>, t: Throwable) {
                    Log.e("API_FAILURE", "Failure: ${t.message}")
                }

            })
    }
}