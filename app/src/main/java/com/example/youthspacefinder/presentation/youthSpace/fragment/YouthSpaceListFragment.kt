package com.example.youthspacefinder.presentation.youthSpace.fragment

import SpacesInfoResponse
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.youthspacefinder.R
import com.example.youthspacefinder.databinding.FragmentYouthSpaceListBinding
import com.example.youthspacefinder.network.RetrofitInstance
import com.example.youthspacefinder.Utils
import com.example.youthspacefinder.presentation.authentication.viewmodel.AuthenticationViewModel
import com.example.youthspacefinder.presentation.youthSpace.adapter.YouthSpaceListAdapter
import com.example.youthspacefinder.presentation.youthSpace.viewmodel.YouthSpaceViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class YouthSpaceListFragment : Fragment() {

    val binding by lazy { FragmentYouthSpaceListBinding.inflate(layoutInflater) }
    val viewModel: YouthSpaceViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        initView()
        initSearchView()
    }

    private fun initView() {
        networking()
    }

    private fun networking() {
        RetrofitInstance.networkServiceOpenAPI.getYouthSpaceList(
            apiKey = Utils.YOUTH_OPEN_API_KEY
        ).enqueue(object : Callback<SpacesInfoResponse> {
            override fun onResponse(
                call: Call<SpacesInfoResponse>,
                response: Response<SpacesInfoResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()!!.youthSpaces
                    binding.recyclerview.adapter = YouthSpaceListAdapter(data, requireContext(), viewModel, "YouthSpaceListFragment")
                } else {
                    Log.e("API_ERROR", "Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<SpacesInfoResponse>, t: Throwable) {
                Log.e("API_FAILURE", "Failure: ${t.message}")
            }

        })
    }

    private fun initSearchView() {
        binding.searchView.setOnQueryTextFocusChangeListener { view, hasFocus ->
            if(hasFocus) {
                findNavController().navigate(R.id.action_youthSpaceListFragment_to_youthSpaceSearchFragment)
            }
        }
    }

    private fun setupListeners() {
        binding.imageView.setOnClickListener {
            findNavController().navigate(R.id.action_youthSpaceListFragment_to_youthSpaceDefineFragment)
        }
        binding.ivUserProfile.setOnClickListener {
            findNavController().navigate(R.id.action_youthSpaceListFragment_to_loginFragment)
        }
    }

}