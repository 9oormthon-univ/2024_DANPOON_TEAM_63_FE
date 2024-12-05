package com.example.youthspacefinder.presentation.youthSpace.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.youthspacefinder.network.RetrofitInstance
import com.example.youthspacefinder.presentation.authentication.viewmodel.AuthenticationViewModel
import com.example.youthspacefinder.presentation.youthSpace.fragment.OnReviewItemChangedListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewDeleteDialog : DialogFragment() {

    private var reviewId: Long? = null
    private var reviewItemChangedListener: OnReviewItemChangedListener ?= null
    val authenticationViewModel: AuthenticationViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(parentFragment is OnReviewItemChangedListener) {
            reviewItemChangedListener = parentFragment as OnReviewItemChangedListener
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
            .setPositiveButton("예") { _, _ ->
                // 후기 삭제 API 연결
                RetrofitInstance.networkServiceBackEnd.deleteSpaceReview(
                    token = authenticationViewModel.userToken!!,
                    reviewId = reviewId!!
                ).enqueue(object:
                    Callback<Any> {
                    override fun onResponse(call: Call<Any>, response: Response<Any>) {
                        if(response.isSuccessful) {
                            reviewItemChangedListener?.onReviewItemChanged(reviewId!!)
                        }
                        else {
                            Log.d("else", "다른 유저가 쓴 글은 삭제하지 못합니다.") // toast 메세지로 띄우기..?
                        }
                    }
                    override fun onFailure(call: Call<Any>, t: Throwable) {
                        TODO("Not yet implemented")
                    }
                })
            }
            .setNegativeButton("아니요") { _, _ ->
                dismiss()
            }
            .create()
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