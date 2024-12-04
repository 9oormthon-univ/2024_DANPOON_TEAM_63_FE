package com.example.youthspacefinder.presentation.youthSpace.fragment

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.youthspacefinder.R
import com.example.youthspacefinder.Utils
import com.example.youthspacefinder.databinding.FragmentYouthSpaceReviewBinding
import com.example.youthspacefinder.model.ReviewRequest
import com.example.youthspacefinder.model.ReviewResponse
import com.example.youthspacefinder.network.RetrofitInstance
import com.example.youthspacefinder.presentation.authentication.viewmodel.AuthenticationViewModel
import com.example.youthspacefinder.presentation.youthSpace.adapter.YouthSpaceReviewAdapter
import com.example.youthspacefinder.presentation.youthSpace.viewmodel.YouthSpaceViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class YouthSpaceReviewFragment : Fragment() {
    val binding by lazy { FragmentYouthSpaceReviewBinding.inflate(layoutInflater) }
    private val youthSpaceViewModel: YouthSpaceViewModel by activityViewModels()
    val authenticationViewModel: AuthenticationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setupListeners()
    }

    private fun initView() {
        binding.tvYouthSpaceName.text = youthSpaceViewModel.spaceName
        binding.recyclerview.adapter = YouthSpaceReviewAdapter(Utils.dummyReviews, requireContext())
        if(authenticationViewModel.isUserLoggedIn) {
            binding.llReviewLoggedIn.visibility = View.VISIBLE
            binding.llReviewLoggedOut.visibility = View.GONE
        } else {
            binding.llReviewLoggedIn.visibility = View.GONE
            binding.llReviewLoggedOut.visibility = View.VISIBLE
        }
        binding.tvNickname.text = authenticationViewModel.id
    }

    private fun setupListeners() {
        binding.ivGoBack.setOnClickListener {
            findNavController().navigate(R.id.action_youthSpaceReviewFragment_to_youthSpaceDetailFragment)
        }
        binding.etWriteComment.setOnEditorActionListener(object : OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    val userComment = binding.etWriteComment.text.toString()
                    val reviewRequest = ReviewRequest(youthSpaceId = youthSpaceViewModel.spaceId!!.toLong(), content = userComment)
                    RetrofitInstance.networkServiceBackEnd.registerSpaceReview(
                        token = authenticationViewModel.userToken!!,
                        reviewRequest = reviewRequest
                    ).enqueue(object: Callback<ReviewResponse> {
                        override fun onResponse(
                            call: Call<ReviewResponse>,
                            response: Response<ReviewResponse>
                        ) {
                            if(response.isSuccessful) {
                                Log.d("server response", "successful")
                                val response = response.body()
                                Log.d("server response", "$response")
                            } else {
                                Log.d("server response", "else")
                            }
                        }

                        override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {
                            Log.d("server response", "${t.message}")
                        }

                    })
                    return true
                } else {
                    return false
                }
            }
        })
    }
}