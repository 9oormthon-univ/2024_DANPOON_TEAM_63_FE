package com.example.youthspacefinder.presentation.youthSpace.adapter

import YouthSpace
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.youthspacefinder.R
import com.example.youthspacefinder.Utils
import com.example.youthspacefinder.presentation.youthSpace.viewmodel.YouthSpaceFavoritesViewModel
import com.example.youthspacefinder.presentation.youthSpace.viewmodel.YouthSpaceInfoViewModel
import kotlin.random.Random

class YouthSpaceListAdapter(
    val youthYouthSpaceItems: List<YouthSpace>,
    val context: Context,
    val youthSpaceInfoViewModel: YouthSpaceInfoViewModel,
    val youthSpaceFavoritesViewModel: YouthSpaceFavoritesViewModel,
    val startFragmentTag: String
) : RecyclerView.Adapter<YouthSpaceListAdapter.YouthSpaceViewHolder>() {
    inner class YouthSpaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val spaceImage: ImageView
        val spaceName: TextView
        val spaceAddress: TextView
        val spaceOperateTime: TextView
        val favoriteSpaceBookmark: ImageView

        init {
            spaceImage = itemView.findViewById(R.id.iv_youth_space)
            spaceName = itemView.findViewById(R.id.tv_youth_space_name)
            spaceAddress = itemView.findViewById(R.id.tv_youth_space_address)
            spaceOperateTime = itemView.findViewById(R.id.tv_youth_space_time)
            favoriteSpaceBookmark = itemView.findViewById(R.id.iv_favorite_space)
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
        val positionSpaceId = youthYouthSpaceItems[position].spcId.toLong()
        if(youthSpaceFavoritesViewModel.userFavoriteSpaces?.contains(positionSpaceId) == true) {
            holder.favoriteSpaceBookmark.visibility = View.VISIBLE
        } else {
            holder.favoriteSpaceBookmark.visibility = View.INVISIBLE // gone 으로 처리하면 청년 공간에 대한 content 가 길 경우 레이아웃 배치가 안맞을 것 같음 → invisible 로 처리
        }
        holder.itemView.setOnClickListener { view ->
            youthSpaceInfoViewModel.spaceImage = randomImage
            youthSpaceInfoViewModel.spaceName = youthYouthSpaceItems[position].spcName
            youthSpaceInfoViewModel.spaceId = youthYouthSpaceItems[position].spcId
            youthSpaceInfoViewModel.spaceAddress = youthYouthSpaceItems[position].address
            youthSpaceInfoViewModel.spaceTime = youthYouthSpaceItems[position].spcTime
            youthSpaceInfoViewModel.operateOrgan =  youthYouthSpaceItems[position].operOrgan
            youthSpaceInfoViewModel.homepageUrl = youthYouthSpaceItems[position].homepage
            youthSpaceInfoViewModel.telephoneNumber = youthYouthSpaceItems[position].telNo
            youthSpaceInfoViewModel.spaceOpenDate = youthYouthSpaceItems[position].openDate
            youthSpaceInfoViewModel.applyTarget = youthYouthSpaceItems[position].applyTarget
            youthSpaceInfoViewModel.spaceCost = youthYouthSpaceItems[position].spcCost
            youthSpaceInfoViewModel.foodYn = youthYouthSpaceItems[position].foodYn
            when(startFragmentTag) {
                "YouthSpaceListFragment" -> view.findNavController().navigate(R.id.action_youthSpaceListFragment_to_youthSpaceDetailFragment)
                "YouthSpaceSearchFragment" -> view.findNavController().navigate(R.id.action_youthSpaceSearchFragment_to_youthSpaceDetailFragment)
            }
        }
    }
}