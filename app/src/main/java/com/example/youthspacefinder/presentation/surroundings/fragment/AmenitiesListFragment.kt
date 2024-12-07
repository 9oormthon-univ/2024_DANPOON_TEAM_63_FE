package com.example.youthspacefinder.presentation.surroundings.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.youthspacefinder.databinding.FragmentAmenitiesListBinding

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
//        RetrofitInstance.networkServiceOpenAPI.getYouthSpaceList(Utils.YOUTH_OPEN_API_KEY, Utils.regionCode["서울"]!!)
//            .enqueue(object : Callback<SpacesInfoResponse> {
//                override fun onResponse(
//                    call: Call<SpacesInfoResponse>,
//                    response: Response<SpacesInfoResponse>
//                ) {
//                    if (response.isSuccessful) {
//                        val data = response.body()!!.youthSpaces
//                        binding.recyclerview.adapter = AmenitiesListAdapter(data, requireContext())
//                    } else {
//                        Log.e("API_ERROR", "Error: ${response.errorBody()?.string()}")
//                    }
//                }
//
//                override fun onFailure(call: Call<SpacesInfoResponse>, t: Throwable) {
//                    Log.e("API_FAILURE", "Failure: ${t.message}")
//                }
//
//            })
    }
}