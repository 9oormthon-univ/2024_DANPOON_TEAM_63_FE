package com.example.youthspacefinder.presentation.youthSpace.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.youthspacefinder.databinding.DialogModifyReviewBinding
import com.example.youthspacefinder.model.ReviewRequest
import com.example.youthspacefinder.model.ReviewResponse
import com.example.youthspacefinder.network.RetrofitInstance
import com.example.youthspacefinder.presentation.authentication.viewmodel.AuthenticationViewModel
import com.example.youthspacefinder.presentation.youthSpace.fragment.OnReviewItemListener
import com.example.youthspacefinder.presentation.youthSpace.viewmodel.YouthSpaceInfoViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewModifyDialog: DialogFragment() {
    lateinit var binding: DialogModifyReviewBinding
    private var reviewId: Long? = null
    private var reviewItemListener: OnReviewItemListener?= null
    val authenticationViewModel: AuthenticationViewModel by activityViewModels()
    val youthSpaceInfoViewModel: YouthSpaceInfoViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(parentFragment is OnReviewItemListener) {
            reviewItemListener = parentFragment as OnReviewItemListener
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            reviewId = it.getLong(ReviewDeleteDialog.REVIEW_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogModifyReviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnModifyReview.setOnClickListener {
            val newContent = binding.etModifyReview.text.toString()
            if(newContent.isBlank()) {
                Toast.makeText(requireContext(), "내용을 입력해주세요!", Toast.LENGTH_SHORT).show()
            } else {
                RetrofitInstance.networkServiceBackEnd.modifySpaceReview(
                    token = authenticationViewModel.userToken!!,
                    reviewId = reviewId!!,
                    modifyReviewRequest = ReviewRequest(
                        content = newContent,
                        youthSpaceId = youthSpaceInfoViewModel.spaceId!!.toLong()
                    )
                ).enqueue(object: Callback<ReviewResponse> {
                    override fun onResponse(
                        call: Call<ReviewResponse>,
                        response: Response<ReviewResponse>
                    ) {
                        if(response.isSuccessful) {
                            val response = response.body()!!
                            reviewItemListener?.onReviewItemChanged(response)
                            dismiss()
                        }
                    }
                    override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {
                        TODO("Not yet implemented")
                    }
                })
            }
        }
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }

    companion object {
        const val DIALOG_TAG = "ReviewModifyDialog"
        const val REVIEW_ID = "review_id"

        @JvmStatic
        fun newInstance(reviewId: Long) =
            ReviewModifyDialog().apply {
                arguments = Bundle().apply {
                    putLong(REVIEW_ID, reviewId)
                }
            }
    }
}