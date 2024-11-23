package com.example.youthspacefinder.presentation.surroundings

import AmenitiesResponse
import android.content.Context
import android.content.Intent
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
        val amenityDistance: TextView

        init {
            amenityName = itemView.findViewById(R.id.tv_amenity_name)
            amenityPhoneNumber = itemView.findViewById(R.id.tv_amenity_phone_number)
            amenityDistance = itemView.findViewById(R.id.tv_distance)
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
        holder.amenityDistance.text = "${amenityItems[position].distance}m"
        holder.itemView.setOnClickListener {
            val intent = Intent(context, AmenitiesWebViewActivity::class.java)
            intent.putExtra("hompage_url", amenityItems[position].placeUrl)
            context.startActivity(intent)
        }
    }
}