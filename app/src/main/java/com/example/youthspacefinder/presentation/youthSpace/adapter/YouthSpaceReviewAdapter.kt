package com.example.youthspacefinder.presentation.youthSpace.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.youthspacefinder.R
import com.example.youthspacefinder.model.ReviewResponse
import com.example.youthspacefinder.presentation.authentication.viewmodel.AuthenticationViewModel
import com.example.youthspacefinder.presentation.youthSpace.fragment.OnReviewItemListener
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField

class YouthSpaceReviewAdapter(
    val userReviews: ArrayList<ReviewResponse>,
    val context: Context,
    val listener: OnReviewItemListener,
    val authenticationViewModel: AuthenticationViewModel
): RecyclerView.Adapter<YouthSpaceReviewAdapter.YouthSpaceReviewViewHolder>() {
    inner class YouthSpaceReviewViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val userNickname: TextView
        val reviewDatetime: TextView
        val reviewContent: TextView
        val reviewOption: LinearLayout
        init {
            userNickname = itemView.findViewById(R.id.tv_nickname)
            reviewDatetime = itemView.findViewById(R.id.tv_review_datetime)
            reviewContent = itemView.findViewById(R.id.tv_review_content)
            reviewOption = itemView.findViewById(R.id.ll_show_more)
            if(authenticationViewModel.isUserLoggedIn) reviewOption.visibility = View.VISIBLE
            else reviewOption.visibility = View.INVISIBLE
            reviewOption.setOnClickListener {
                showPopUpMenu(it, userReviews[adapterPosition].reviewId, userReviews[adapterPosition].content)
            }
        }
    }

    private fun showPopUpMenu(anchorView: View, reviewId: Long, reviewContent: String) {
        val popupMenu = PopupMenu(context, anchorView)
        popupMenu.menuInflater.inflate(R.menu.review_option, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId) {
                R.id.modify -> {
                    listener.onReviewOptionModifyClicked(reviewId, reviewContent)
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
        holder.reviewDatetime.text = formatTimeWithTimeZone(userReviews[position].updateAt)
        holder.reviewContent.text = userReviews[position].content
    }

    fun formatTimeWithTimeZone(inputTime: String): String {
        val inputFormatter = DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-dd'T'HH:mm:ss")
            .optionalStart() // 소수 초 부분을 선택적으로 허용
            .appendFraction(ChronoField.NANO_OF_SECOND, 0, 9, true)
            .optionalEnd()
            .toFormatter()

        val dateTime = LocalDateTime.parse(inputTime, inputFormatter)

        // UTC를 KST로 변환
        val kstDateTime = dateTime.atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.of("Asia/Seoul"))

        // 원하는 형식으로 변환
        val outputFormatter = DateTimeFormatter.ofPattern("yyyy. MM. dd a h:mm")
        return kstDateTime.format(outputFormatter)
    }
}