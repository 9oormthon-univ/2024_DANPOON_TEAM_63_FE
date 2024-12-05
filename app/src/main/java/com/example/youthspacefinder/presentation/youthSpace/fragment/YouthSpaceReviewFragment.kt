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
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.youthspacefinder.R
import com.example.youthspacefinder.databinding.FragmentYouthSpaceReviewBinding
import com.example.youthspacefinder.model.ReviewRequest
import com.example.youthspacefinder.model.ReviewResponse
import com.example.youthspacefinder.network.RetrofitInstance
import com.example.youthspacefinder.presentation.authentication.viewmodel.AuthenticationViewModel
import com.example.youthspacefinder.presentation.youthSpace.adapter.YouthSpaceReviewAdapter
import com.example.youthspacefinder.presentation.youthSpace.dialog.ReviewDeleteDialog
import com.example.youthspacefinder.presentation.youthSpace.dialog.ReviewModifyDialog
import com.example.youthspacefinder.presentation.youthSpace.viewmodel.YouthSpaceInfoViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class YouthSpaceReviewFragment : Fragment(), OnReviewItemListener {
    val binding by lazy { FragmentYouthSpaceReviewBinding.inflate(layoutInflater) }
    private val youthSpaceInfoViewModel: YouthSpaceInfoViewModel by activityViewModels()
    val authenticationViewModel: AuthenticationViewModel by activityViewModels()
    var userReviews = arrayListOf<ReviewResponse>()

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
        binding.tvYouthSpaceName.text = youthSpaceInfoViewModel.spaceName
        if (authenticationViewModel.isUserLoggedIn) {
            binding.llReviewLoggedIn.visibility = View.VISIBLE
            binding.llReviewLoggedOut.visibility = View.GONE
        } else {
            binding.llReviewLoggedIn.visibility = View.GONE
            binding.llReviewLoggedOut.visibility = View.VISIBLE
        }
        binding.tvNickname.text = authenticationViewModel.id
        networking()
    }

    private fun networking() {
        RetrofitInstance.networkServiceBackEnd.getSpaceReviews(youthSpaceInfoViewModel.spaceId!!.toLong())
            .enqueue(object : Callback<ArrayList<ReviewResponse>> {
                override fun onResponse(
                    call: Call<ArrayList<ReviewResponse>>,
                    response: Response<ArrayList<ReviewResponse>>
                ) {
                    if (response.isSuccessful) {
                        userReviews = response.body()!!
                        if (userReviews.isEmpty()) {
                            binding.recyclerview.visibility = View.GONE
                            binding.llReviewEmpty.visibility = View.VISIBLE
                        } else {
                            binding.recyclerview.visibility = View.VISIBLE
                            binding.llReviewEmpty.visibility = View.GONE
                            binding.recyclerview.adapter = YouthSpaceReviewAdapter(
                                userReviews,
                                requireContext(),
                                this@YouthSpaceReviewFragment
                            )
                        }
                    } else {
                        Log.d("server response", "else")
                    }
                }

                override fun onFailure(call: Call<ArrayList<ReviewResponse>>, t: Throwable) {
                    Log.d("server response", "${t.message}")
                }

            })

    }

    private fun setupListeners() {
        binding.ivGoBack.setOnClickListener {
            findNavController().navigate(R.id.action_youthSpaceReviewFragment_to_youthSpaceDetailFragment)
        }
        binding.etWriteComment.setOnEditorActionListener(object : OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    val userComment = binding.etWriteComment.text.toString()
                    val reviewRequest = ReviewRequest(
                        youthSpaceId = youthSpaceInfoViewModel.spaceId!!.toLong(),
                        content = userComment
                    )
                    RetrofitInstance.networkServiceBackEnd.registerSpaceReview(
                        token = authenticationViewModel.userToken!!,
                        reviewRequest = reviewRequest
                    ).enqueue(object : Callback<ReviewResponse> {
                        override fun onResponse(
                            call: Call<ReviewResponse>,
                            response: Response<ReviewResponse>
                        ) {
                            if (response.isSuccessful) {
                                val newReview = response.body()
                                Toast.makeText(requireContext(), "후기가 등록되었습니다!", Toast.LENGTH_SHORT)
                                    .show()
                                binding.etWriteComment.setText("")
                                userReviews.add(newReview!!)
                                val newReviewPosition = userReviews.size - 1
                                binding.recyclerview.adapter?.notifyItemInserted(newReviewPosition)
                                binding.llReviewEmpty.visibility = View.GONE
                            } else {

                            }
                        }

                        override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {

                        }

                    })
                    return true
                } else {
                    return false
                }
            }
        })
    }

    override fun onReviewOptionDeleteClicked(reviewId: Long) {
        val reviewDeleteDialog = ReviewDeleteDialog.newInstance(reviewId)
        reviewDeleteDialog.show(
            childFragmentManager, ReviewDeleteDialog.DIALOG_TAG
        )
    }

    override fun onReviewOptionModifyClicked(reviewId: Long) {
        val reviewModifyDialog = ReviewModifyDialog.newInstance(reviewId)
        reviewModifyDialog.show(
            childFragmentManager, ReviewModifyDialog.DIALOG_TAG
        )
    }

    override fun onReviewItemDeleted(reviewId: Long) {
        val deleteReviewPosition = userReviews.indexOfFirst {
            it.reviewId == reviewId
        }
        userReviews.removeAt(deleteReviewPosition)
        binding.recyclerview.adapter?.notifyItemRemoved(deleteReviewPosition)
        if(userReviews.isEmpty()) {
            binding.llReviewEmpty.visibility = View.VISIBLE
        }
        Toast.makeText(requireContext(), "후기가 삭제되었습니다!", Toast.LENGTH_SHORT).show()
    }

    override fun onReviewItemChanged(review: ReviewResponse) {
        val modifyReviewPosition = userReviews.indexOfFirst {
            it.reviewId == review.reviewId
        }
        userReviews[modifyReviewPosition] = review
        binding.recyclerview.adapter?.notifyItemChanged(modifyReviewPosition)
        Toast.makeText(requireContext(), "후기가 수정되었습니다!", Toast.LENGTH_SHORT).show()
    }
}