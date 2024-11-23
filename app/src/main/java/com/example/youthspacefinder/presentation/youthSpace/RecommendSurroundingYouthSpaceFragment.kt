package com.example.youthspacefinder.presentation.youthSpace

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.youthspacefinder.R
import com.example.youthspacefinder.databinding.FragmentRecommendSorroundingYouthSpaceBinding

class RecommendSurroundingYouthSpaceFragment : Fragment() {

    val binding by lazy { FragmentRecommendSorroundingYouthSpaceBinding.inflate(layoutInflater) }

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
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_recommendSurroundingYouthSpaceFragment_to_youthSpaceListFragment)
        }
    }
}