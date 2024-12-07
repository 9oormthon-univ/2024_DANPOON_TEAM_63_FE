package com.example.youthspacefinder.presentation.youthSpace.fragment

import com.example.youthspacefinder.model.ReviewResponse

interface OnReviewItemListener {
    fun onReviewOptionDeleteClicked(reviewId: Long)
    fun onReviewOptionModifyClicked(reviewId: Long, reviewContent: String)
    fun onReviewItemDeleted(reviewId: Long)
    fun onReviewItemChanged(review: ReviewResponse)
}