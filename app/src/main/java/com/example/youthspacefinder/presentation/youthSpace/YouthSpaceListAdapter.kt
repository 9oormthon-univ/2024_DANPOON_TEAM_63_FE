package com.example.youthspacefinder.presentation.youthSpace

import YouthSpace
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.youthspacefinder.R
import com.example.youthspacefinder.utils
import kotlin.random.Random

class YouthSpaceListAdapter(
    val youthYouthSpaceItems: List<YouthSpace>,
    val context: Context
) : RecyclerView.Adapter<YouthSpaceListAdapter.YouthSpaceViewHolder>() {
    inner class YouthSpaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val spaceImage: ImageView
        val spaceName: TextView
        val spaceAddress: TextView
        val spaceOperateTime: TextView

        init {
            spaceImage = itemView.findViewById(R.id.iv_youth_space)
            spaceName = itemView.findViewById(R.id.tv_youth_space_name)
            spaceAddress = itemView.findViewById(R.id.tv_youth_space_address)
            spaceOperateTime = itemView.findViewById(R.id.tv_youth_space_time)

            itemView.setOnClickListener {

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YouthSpaceViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_youth_space, parent, false)
        return YouthSpaceViewHolder(view)
    }

    override fun getItemCount(): Int {
        return youthYouthSpaceItems.size
    }

    override fun onBindViewHolder(holder: YouthSpaceViewHolder, position: Int) {
        val randomImage = utils.youthSpaceImageList[Random.nextInt(utils.youthSpaceImageList.size)]
        Glide.with(context).load(randomImage).into(holder.spaceImage)
        holder.spaceName.text = youthYouthSpaceItems[position].spcName
        holder.spaceAddress.text = youthYouthSpaceItems[position].address
        holder.spaceOperateTime.text = youthYouthSpaceItems[position].officeHours
    }
}