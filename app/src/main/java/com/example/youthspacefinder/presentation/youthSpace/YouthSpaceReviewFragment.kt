package com.example.youthspacefinder.presentation.youthSpace

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.youthspacefinder.R
import com.example.youthspacefinder.Utils
import com.example.youthspacefinder.databinding.FragmentYouthSpaceReviewBinding
import com.example.youthspacefinder.presentation.youthSpace.adapter.YouthSpaceReviewAdapter

class YouthSpaceReviewFragment : Fragment() {
    val binding by lazy { FragmentYouthSpaceReviewBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setupListeners()
    }

    private fun initView() {
        binding.recyclerview.adapter = YouthSpaceReviewAdapter(Utils.dummyReviews, requireContext())
    }

    private fun setupListeners() {
        binding.ivGoBack.setOnClickListener {
            findNavController().navigate(R.id.action_youthSpaceReviewFragment_to_youthSpaceDetailFragment)
        }
    }
}