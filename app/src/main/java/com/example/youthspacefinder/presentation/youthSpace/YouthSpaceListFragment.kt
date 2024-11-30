package com.example.youthspacefinder.presentation.youthSpace

import SpacesInfoResponse
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.youthspacefinder.R
import com.example.youthspacefinder.databinding.FragmentYouthSpaceListBinding
import com.example.youthspacefinder.network.RetrofitInstance
import com.example.youthspacefinder.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class YouthSpaceListFragment : Fragment() {

    val binding by lazy { FragmentYouthSpaceListBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        initSearchView()
    }

    private fun initSearchView() {
        binding.searchView.isSubmitButtonEnabled = true
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d("searchView", query!!)
                val regionCode = filterUserInput(query)
                if (regionCode.isEmpty()) {
                    Toast.makeText(requireContext(), "정확한 주소를 입력하지 않았습니다!", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Log.d("searchView", regionCode)
                    networking(regionCode)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }

    private fun networking(regionCode: String) {
        // regionCode 의 길이가 9자리면 시,도 코드 이고 12자리면 시,군,구 코드이다.
        val codeSize = regionCode.length
        var cityCode = ""
        var areaCode = ""
        if (codeSize == 9) cityCode = regionCode
        else if (codeSize == 12) areaCode = regionCode
        RetrofitInstance.networkServiceOpenAPI.getYouthSpaceList(
            apiKey = Utils.YOUTH_OPEN_API_KEY,
            cityCode = cityCode,
            areaCode = areaCode
        ).enqueue(object : Callback<SpacesInfoResponse> {
            override fun onResponse(
                call: Call<SpacesInfoResponse>,
                response: Response<SpacesInfoResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()!!.youthSpaces
                    binding.recyclerview.adapter = YouthSpaceListAdapter(data, requireContext())
                } else {
                    Log.e("API_ERROR", "Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<SpacesInfoResponse>, t: Throwable) {
                Log.e("API_FAILURE", "Failure: ${t.message}")
            }

        })
    }

    private fun filterUserInput(userInput: String): String {
        val cityInfo = Utils.regionCode.filter { (city, _) ->
            userInput.contains(city)
        }
        if (cityInfo.isEmpty()) {
            // 사용자가 시도를 입력하지 않은 경우
            return ""
        } else {
            // 사용자가 시도를 입력한 경우 → 시,군,구까지 입력했는지 확인해야 함
            val districtsInfo =
                cityInfo.values.first()["districts"] as Map<String, String>  // Collection<Map<String, Any>> → Map<String, Any>
            val districtInfo = districtsInfo.filter { (gu, _) ->
                userInput.contains(gu)
            }
            if (districtInfo.isEmpty()) {
                // 사용자가 시, 군, 구 까지 입력하지 않은 경우 → 시, 도 만 출력
                return cityInfo.values.first()["code"] as String // 시,도 에 해당하는 지역코드 반환
            } else {
                return districtInfo.values.first()
            }
        }
    }

    private fun setupListeners() {
        binding.imageView.setOnClickListener {
            findNavController().navigate(R.id.action_youthSpaceListFragment_to_youthSpaceDefineFragment)
        }
//        binding.ivUserProfile.setOnClickListener {
//            findNavController().navigate(R.id.action_youthSpaceListFragment_to_loginFragment)
//        }
    }

}