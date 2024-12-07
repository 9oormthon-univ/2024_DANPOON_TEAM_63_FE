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
    private var reviewContent: String? = null
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
            reviewId = it.getLong(REVIEW_ID)
            reviewContent = it.getString(REVIEW_CONTENT)
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
        initView()
        setupListeners()

    }

    private fun initView() {
        binding.etModifyReview.setText(reviewContent)
    }

    private fun setupListeners() {
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
                            Toast.makeText(requireContext(), "후기가 수정되었습니다!", Toast.LENGTH_SHORT).show()
                            dismiss()
                        } else {
                            Toast.makeText(requireContext(), "다른 사람의 후기는 수정하지 못합니다!", Toast.LENGTH_SHORT).show()
                        }
                    }
                    override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {
                        Toast.makeText(requireContext(), "다른 사람의 후기는 수정하지 못합니다!", Toast.LENGTH_SHORT).show()
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
        const val REVIEW_CONTENT = "review_content"

        @JvmStatic
        fun newInstance(reviewId: Long, reviewContent: String) =
            ReviewModifyDialog().apply {
                arguments = Bundle().apply {
                    putLong(REVIEW_ID, reviewId)
                    putString(REVIEW_CONTENT, reviewContent)
                }
            }
    }
}