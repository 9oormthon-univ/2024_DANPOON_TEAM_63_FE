package com.example.youthspacefinder.presentation.youthSpace.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.youthspacefinder.R
import com.example.youthspacefinder.model.UserReviewInfo

class YouthSpaceReviewAdapter(
    val userReviews: ArrayList<UserReviewInfo>,
    val context: Context
): RecyclerView.Adapter<YouthSpaceReviewAdapter.YouthSpaceReviewViewHolder>() {
    inner class YouthSpaceReviewViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val userNickname: TextView
        val reviewDatetime: TextView
        val reviewContent: TextView
        init {
            userNickname = itemView.findViewById(R.id.tv_nickname)
            reviewDatetime = itemView.findViewById(R.id.tv_review_datetime)
            reviewContent = itemView.findViewById(R.id.tv_review_content)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YouthSpaceReviewViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_user_review, parent, false)
        return YouthSpaceReviewViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return userReviews.size
    }

    override fun onBindViewHolder(holder: YouthSpaceReviewViewHolder, position: Int) {
        holder.userNickname.text = userReviews[position].nickname
        holder.reviewDatetime.text = userReviews[position].dateTime
        holder.reviewContent.text = userReviews[position].content
    }
}