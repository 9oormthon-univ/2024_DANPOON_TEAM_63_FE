package com.example.youthspacefinder.presentation.surroundings

import YouthSpace
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.youthspacefinder.R
import com.example.youthspacefinder.utils
import kotlin.random.Random

class AmenitiesListAdapter(
    val youthYouthSpaceItems: List<YouthSpace>,
    val context: Context
) : RecyclerView.Adapter<AmenitiesListAdapter.AmenitiesListViewHolder>() {
    inner class AmenitiesListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val spaceImage: ImageView
        val spaceName: TextView
        val spaceAddress: TextView
        val spaceOperateTime: TextView

        init {
            spaceImage = itemView.findViewById(R.id.iv_youth_space)
            spaceName = itemView.findViewById(R.id.tv_youth_space_name)
            spaceAddress = itemView.findViewById(R.id.tv_youth_space_address)
            spaceOperateTime = itemView.findViewById(R.id.tv_youth_space_time)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AmenitiesListViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_youth_space, parent, false)
        return AmenitiesListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return youthYouthSpaceItems.size
    }

    override fun onBindViewHolder(holder: AmenitiesListViewHolder, position: Int) {
        val randomImage = utils.youthSpaceImageList[Random.nextInt(utils.youthSpaceImageList.size)]
        Glide.with(context).load(randomImage).into(holder.spaceImage)
        holder.spaceName.text = youthYouthSpaceItems[position].spcName
        holder.spaceAddress.text = youthYouthSpaceItems[position].address
        holder.spaceOperateTime.text = youthYouthSpaceItems[position].officeHours
    }
}