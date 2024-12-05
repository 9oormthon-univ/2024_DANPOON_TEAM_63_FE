package com.example.youthspacefinder.presentation.youthSpace.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.youthspacefinder.R
import com.example.youthspacefinder.model.ReviewResponse
import com.example.youthspacefinder.presentation.youthSpace.fragment.OnReviewOptionClickListener

class YouthSpaceReviewAdapter(
    val userReviews: ArrayList<ReviewResponse>,
    val context: Context,
    val listener: OnReviewOptionClickListener
): RecyclerView.Adapter<YouthSpaceReviewAdapter.YouthSpaceReviewViewHolder>() {
    inner class YouthSpaceReviewViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val userNickname: TextView
        val reviewDatetime: TextView
        val reviewContent: TextView
        val reviewOption: ImageView
        init {
            userNickname = itemView.findViewById(R.id.tv_nickname)
            reviewDatetime = itemView.findViewById(R.id.tv_review_datetime)
            reviewContent = itemView.findViewById(R.id.tv_review_content)
            reviewOption = itemView.findViewById(R.id.iv_show_more)
            reviewOption.setOnClickListener {
                showPopUpMenu(it, userReviews[adapterPosition].reviewId)
            }
        }
    }

    private fun showPopUpMenu(anchorView: View, reviewId: Long) {
        val popupMenu = PopupMenu(context, anchorView)
        popupMenu.menuInflater.inflate(R.menu.review_option, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId) {
                R.id.modify -> {
                    listener.onReviewOptionModifyClicked(reviewId)
                    true
                }
                R.id.delete -> {
                    listener.onReviewOptionDeleteClicked(reviewId)
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
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
        holder.reviewDatetime.text = userReviews[position].updateAt
        holder.reviewContent.text = userReviews[position].content
    }
}