package com.example.youthspacefinder.presentation.surroundings

import AmenitiesResponse
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.youthspacefinder.R

class AmenitiesListDetailAdapter(
    val amenityItems: List<AmenitiesResponse>,
    val context: Context
): RecyclerView.Adapter<AmenitiesListDetailAdapter.AmenitiesListDetailViewHolder>() {
    inner class AmenitiesListDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val amenityName: TextView
        val amenityPhoneNumber: TextView
        val amenityUrl: TextView
        val amenityPositionX: TextView
        val amenityPositionY: TextView

        init {
            amenityName = itemView.findViewById(R.id.tv_amenity_name)
            amenityPhoneNumber = itemView.findViewById(R.id.tv_amenity_phone_number)
            amenityUrl = itemView.findViewById(R.id.tv_amenity_url)
            amenityPositionX = itemView.findViewById(R.id.tv_amenity_position_x)
            amenityPositionY = itemView.findViewById(R.id.tv_amenity_position_y)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AmenitiesListDetailViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_amenity_space, parent, false)
        return AmenitiesListDetailViewHolder(view)
    }

    override fun getItemCount(): Int {
        return amenityItems.size
    }

    override fun onBindViewHolder(holder: AmenitiesListDetailViewHolder, position: Int) {
        holder.amenityName.text = amenityItems[position].amenityName
        holder.amenityPhoneNumber.text = amenityItems[position].phoneNumber
        holder.amenityUrl.text = amenityItems[position].placeUrl
        holder.amenityPositionX.text = amenityItems[position].positionX
        holder.amenityPositionY.text = amenityItems[position].positionY
        holder.itemView.setOnClickListener { }
    }
}