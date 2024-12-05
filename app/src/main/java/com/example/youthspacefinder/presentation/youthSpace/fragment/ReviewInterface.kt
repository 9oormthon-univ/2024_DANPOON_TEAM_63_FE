package com.example.youthspacefinder.presentation.youthSpace.fragment

interface OnReviewOptionClickListener {
    fun onReviewOptionDeleteClicked(reviewId: Long)
    fun onReviewOptionModifyClicked(reviewId: Long)
}

interface OnReviewItemChangedListener {
    fun onReviewItemChanged(reviewId: Long)
}