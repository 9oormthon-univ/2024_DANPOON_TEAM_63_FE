package com.example.youthspacefinder.presentation.youthSpace

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
import com.example.youthspacefinder.Utils
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
        val randomImage = Utils.youthSpaceImageList[Random.nextInt(Utils.youthSpaceImageList.size)]
        Glide.with(context).load(randomImage).into(holder.spaceImage)
        holder.spaceName.text = youthYouthSpaceItems[position].spcName
        holder.spaceAddress.text = youthYouthSpaceItems[position].address
        holder.spaceOperateTime.text = youthYouthSpaceItems[position].officeHours

        holder.itemView.setOnClickListener { view ->
            val bundle = bundleOf(
                "spcImage" to randomImage,
                "spcName" to youthYouthSpaceItems[position].spcName,
                "address" to youthYouthSpaceItems[position].address,
                "spcTime" to youthYouthSpaceItems[position].spcTime,
                "operOrgan" to youthYouthSpaceItems[position].operOrgan,
                "homepage" to youthYouthSpaceItems[position].homepage,
                "telNo" to youthYouthSpaceItems[position].telNo,
                "openDate" to youthYouthSpaceItems[position].openDate,
                "applyTarget" to youthYouthSpaceItems[position].applyTarget,
                "spcCost" to youthYouthSpaceItems[position].spcCost,
                "foodYn" to youthYouthSpaceItems[position].foodYn
            )
            view.findNavController()
                .navigate(R.id.action_youthSpaceListFragment_to_youthSpaceDetailFragment, bundle)
        }
    }
}