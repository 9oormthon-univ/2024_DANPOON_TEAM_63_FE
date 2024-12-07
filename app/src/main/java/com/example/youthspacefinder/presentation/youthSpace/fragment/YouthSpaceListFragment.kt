package com.example.youthspacefinder.presentation.youthSpace.fragment

import SpacesInfoResponse
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.youthspacefinder.R
import com.example.youthspacefinder.databinding.FragmentYouthSpaceListBinding
import com.example.youthspacefinder.network.RetrofitInstance
import com.example.youthspacefinder.Utils
import com.example.youthspacefinder.model.FavoriteSpaceResponse
import com.example.youthspacefinder.presentation.authentication.viewmodel.AuthenticationViewModel
import com.example.youthspacefinder.presentation.youthSpace.adapter.YouthSpaceListAdapter
import com.example.youthspacefinder.presentation.youthSpace.viewmodel.YouthSpaceFavoritesViewModel
import com.example.youthspacefinder.presentation.youthSpace.viewmodel.YouthSpaceInfoViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class YouthSpaceListFragment : Fragment() {

    val binding by lazy { FragmentYouthSpaceListBinding.inflate(layoutInflater) }
    val youthSpaceInfoViewModel: YouthSpaceInfoViewModel by activityViewModels()
    val authenticationViewModel: AuthenticationViewModel by activityViewModels()
    val youthSpaceFavoritesViewModel: YouthSpaceFavoritesViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("list fragment","onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("list fragment","onCreateView")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("list fragment","onViewCreated")
        setupListeners()
        initView()
        initSearchView()
    }

    override fun onResume() {
        super.onResume()
        Log.d("list fragment","onResume")
    }

    private fun initView() {
        if (authenticationViewModel.isUserLoggedIn) {
            binding.ivAuthentication.visibility = View.GONE
            binding.ivBookmark.visibility = View.VISIBLE
        }
        else {
            binding.ivAuthentication.visibility = View.VISIBLE
            binding.ivBookmark.visibility = View.GONE
        }
        networking()
    }

    private fun networking() {
        if (authenticationViewModel.isUserLoggedIn) {
            // 로그인 상태일 때 해당 유저가 추가한 청년공간 즐겨찾기 리스트 조회하기
            RetrofitInstance.networkServiceBackEnd.getFavoriteSpaceList(
                token = authenticationViewModel.userToken!!
            ).enqueue(object : Callback<FavoriteSpaceResponse> {
                override fun onResponse(
                    call: Call<FavoriteSpaceResponse>,
                    response: Response<FavoriteSpaceResponse>
                ) {
                    if (response.isSuccessful) {
                        val response = response.body()
                        val favoriteSpaceIds = response?.favoriteSpaceIds
                        youthSpaceFavoritesViewModel.userFavoriteSpaceIds = favoriteSpaceIds
                    }
                }

                override fun onFailure(call: Call<FavoriteSpaceResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        }
        RetrofitInstance.networkServiceOpenAPI.getYouthSpaceList(
            apiKey = Utils.YOUTH_OPEN_API_KEY
        ).enqueue(object : Callback<SpacesInfoResponse> {
            override fun onResponse(
                call: Call<SpacesInfoResponse>,
                response: Response<SpacesInfoResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()!!.youthSpaces
                    binding.recyclerview.adapter = YouthSpaceListAdapter(
                        data,
                        requireContext(),
                        youthSpaceInfoViewModel,
                        youthSpaceFavoritesViewModel,
                        "YouthSpaceListFragment"
                    )
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
            if (hasFocus) {
                findNavController().navigate(R.id.action_youthSpaceListFragment_to_youthSpaceSearchFragment)
            }
        }
    }

    private fun setupListeners() {
        binding.imageView.setOnClickListener {
            findNavController().navigate(R.id.action_youthSpaceListFragment_to_youthSpaceDefineFragment)
        }
        binding.ivAuthentication.setOnClickListener {
            findNavController().navigate(R.id.action_youthSpaceListFragment_to_loginFragment)
        }
        binding.ivUserProfile.setOnClickListener {
            if (authenticationViewModel.isUserLoggedIn) {
                findNavController().navigate(R.id.action_youthSpaceListFragment_to_settingsUserLoggedInFragment)
            } else {
                findNavController().navigate(R.id.action_youthSpaceListFragment_to_settingsUserLoggedOutFragment)
            }
        }
        binding.ivBookmark.setOnClickListener {
            findNavController().navigate(R.id.action_youthSpaceListFragment_to_youthSpaceBookmarkFragment)
        }
    }

}