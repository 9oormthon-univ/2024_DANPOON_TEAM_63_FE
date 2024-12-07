package com.example.youthspacefinder.presentation.youthSpace.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.youthspacefinder.network.RetrofitInstance
import com.example.youthspacefinder.presentation.authentication.viewmodel.AuthenticationViewModel
import com.example.youthspacefinder.presentation.youthSpace.fragment.OnReviewItemListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewDeleteDialog : DialogFragment() {

    private var reviewId: Long? = null
    private var reviewItemListener: OnReviewItemListener ?= null
    val authenticationViewModel: AuthenticationViewModel by activityViewModels()

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
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setMessage("작성한 후기를 삭제하시겠습니까?")
            .setPositiveButton("예", null)
            .setNegativeButton("아니요") { _, _ -> dismiss() }
            .create()
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog as? AlertDialog
        dialog?.getButton(AlertDialog.BUTTON_POSITIVE)?.setOnClickListener {
            // 후기 삭제 API 연결
            RetrofitInstance.networkServiceBackEnd.deleteSpaceReview(
                token = authenticationViewModel.userToken!!,
                reviewId = reviewId!!
            ).enqueue(object : Callback<Any> {
                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    if (response.isSuccessful) {
                        reviewItemListener?.onReviewItemDeleted(reviewId!!)
                        dismiss()
                    } else {
                        Toast.makeText(requireContext(), "다른 사람의 후기는 수정하지 못합니다!", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    Toast.makeText(requireContext(), "다른 사람의 후기는 수정하지 못합니다!", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    companion object {
        const val DIALOG_TAG = "ReviewDeleteDialog"
        const val REVIEW_ID = "review_id"

        @JvmStatic
        fun newInstance(reviewId: Long) =
            ReviewDeleteDialog().apply {
                arguments = Bundle().apply {
                    putLong(REVIEW_ID, reviewId)
                }
            }
    }
}