package com.example.youthspacefinder.presentation.youthSpace.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.youthspacefinder.R
import com.example.youthspacefinder.Utils
import com.example.youthspacefinder.databinding.FragmentYouthSpaceReviewBinding
import com.example.youthspacefinder.presentation.youthSpace.adapter.YouthSpaceReviewAdapter
import com.example.youthspacefinder.presentation.youthSpace.viewmodel.YouthSpaceViewModel

class YouthSpaceReviewFragment : Fragment() {
    val binding by lazy { FragmentYouthSpaceReviewBinding.inflate(layoutInflater) }
    val viewModel: YouthSpaceViewModel by activityViewModels()

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
        binding.tvYouthSpaceName.text = viewModel.spaceName
        binding.recyclerview.adapter = YouthSpaceReviewAdapter(Utils.dummyReviews, requireContext())
    }

    private fun setupListeners() {
        binding.ivGoBack.setOnClickListener {
            findNavController().navigate(R.id.action_youthSpaceReviewFragment_to_youthSpaceDetailFragment)
        }
    }
}