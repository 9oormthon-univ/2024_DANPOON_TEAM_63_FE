package com.example.youthspacefinder.Surroundings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.youthspacefinder.R
import com.example.youthspacefinder.databinding.FragmentAmenitiesListBinding
import com.example.youthspacefinder.databinding.FragmentRecommendSorroundingYouthSpaceBinding

class AmenitiesListFragment : Fragment() {
    val binding by lazy { FragmentAmenitiesListBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        binding.btnAmenitiesRouteGuide.setOnClickListener {
            findNavController().navigate(R.id.action_amenitiesListFragment_to_amenitiesRouteGuideFragment)
        }
    }
}