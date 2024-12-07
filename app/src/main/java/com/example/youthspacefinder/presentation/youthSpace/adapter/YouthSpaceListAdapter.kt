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
    val youthSpaceItems: List<YouthSpace>,
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
        return youthSpaceItems.size
    }

    override fun onBindViewHolder(holder: YouthSpaceViewHolder, position: Int) {
        val randomImage = Utils.youthSpaceImageList[Random.nextInt(Utils.youthSpaceImageList.size)]
        Glide.with(context).load(randomImage).into(holder.spaceImage)
        holder.spaceName.text = youthSpaceItems[position].spcName
        holder.spaceAddress.text = youthSpaceItems[position].address
        if(youthSpaceItems[position].officeHours != "null") holder.spaceOperateTime.text = youthSpaceItems[position].officeHours

        val positionSpaceId = youthSpaceItems[position].spcId.toLong()
        if(youthSpaceFavoritesViewModel.userFavoriteSpaceIds?.contains(positionSpaceId) == true) {
            holder.favoriteSpaceBookmark.visibility = View.VISIBLE
        } else {
            holder.favoriteSpaceBookmark.visibility = View.INVISIBLE // gone 으로 처리하면 청년 공간에 대한 content 가 길 경우 레이아웃 배치가 안맞을 것 같음 → invisible 로 처리
        }

        holder.itemView.setOnClickListener { view ->
            // 실질적으로 쓰는 데이터
            youthSpaceInfoViewModel.spaceAddress = youthSpaceItems[position].address
            youthSpaceInfoViewModel.spaceImage = randomImage
            youthSpaceInfoViewModel.spaceName = youthSpaceItems[position].spcName
            youthSpaceInfoViewModel.spcTime = youthSpaceItems[position].spcTime
            youthSpaceInfoViewModel.telephoneNumber = youthSpaceItems[position].telNo
            youthSpaceInfoViewModel.spaceId = youthSpaceItems[position].spcId
            youthSpaceInfoViewModel.homepageUrl = youthSpaceItems[position].homepage
            youthSpaceInfoViewModel.officeHours = youthSpaceItems[position].officeHours

            // 실질적으로 쓰지 않는 데이터
            youthSpaceInfoViewModel.operateOrgan =  youthSpaceItems[position].operOrgan
            youthSpaceInfoViewModel.spaceOpenDate = youthSpaceItems[position].openDate
            youthSpaceInfoViewModel.applyTarget = youthSpaceItems[position].applyTarget
            youthSpaceInfoViewModel.spaceCost = youthSpaceItems[position].spcCost
            youthSpaceInfoViewModel.foodYn = youthSpaceItems[position].foodYn

            when(startFragmentTag) {
                "YouthSpaceListFragment" -> view.findNavController().navigate(R.id.action_youthSpaceListFragment_to_youthSpaceDetailFragment)
                "YouthSpaceSearchFragment" -> view.findNavController().navigate(R.id.action_youthSpaceSearchFragment_to_youthSpaceDetailFragment)
                "YouthSpaceBookmarkFragment" -> view.findNavController().navigate(R.id.action_youthSpaceBookmarkFragment_to_youthSpaceDetailFragment)
            }
        }
    }
}