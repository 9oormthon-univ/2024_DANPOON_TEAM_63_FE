package com.example.youthspacefinder.presentation.youthSpace

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.youthspacefinder.databinding.FragmentYouthSpaceKaoKaoMapBinding

class YouthSpaceKaKaoMapFragment : Fragment() {

    val binding by lazy { FragmentYouthSpaceKaoKaoMapBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

}