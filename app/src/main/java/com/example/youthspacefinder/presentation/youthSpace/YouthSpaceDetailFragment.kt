package com.example.youthspacefinder.presentation.youthSpace

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.youthspacefinder.R
import com.example.youthspacefinder.databinding.FragmentYouthSpaceDetailBinding

class YouthSpaceDetailFragment : Fragment() {
    val binding by lazy { FragmentYouthSpaceDetailBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupListeners()
    }

    private fun setupViews() {
        val spcImage = requireArguments().getInt("spcImage")
        val spcName = requireArguments().getString("spcName")
        val address = requireArguments().getString("address")
        val spcTime = requireArguments().getString("spcTime")
//        val operOrgan = requireArguments().getString("operOrgan")
//        val homepage = requireArguments().getString("homepage")
        val telNo = requireArguments().getString("telNo")
//        val openDate = requireArguments().getString("openDate")
//        val applyTarget = requireArguments().getString("applyTarget")
//        val spcCost = requireArguments().getString("spcCost")
//        val foodYn = requireArguments().getString("foodYn")
        binding.apply {
            Glide.with(requireContext()).load(spcImage).into(ivSpcImage)
            tvSpcName.text = spcName
            tvAddress.text = address
            tvSpcTime.text = spcTime
//            tvOperOrgan.text = operOrgan
//            tvHomepage.text = homepage
            tvTelNo.text = telNo
//            tvOpenDate.text = openDate
//            tvApplyTarget.text = applyTarget
//            tvSpcCost.text = spcCost
//            tvFoodYn.text = foodYn
        }
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_youthSpaceDetailFragment_to_youthSpaceListFragment)
        }
        binding.btnSearchSurroundingAmenities.setOnClickListener {
            val bundle = bundleOf(
                "youth_space_name" to binding.tvSpcName.text.toString(),
                "youth_space_address" to binding.tvAddress.text.toString()
            )
            findNavController().navigate(R.id.action_youthSpaceDetailFragment_to_youthSpaceKaKaoMapFragment, bundle)
        }
        binding.btnGoToUrl.setOnClickListener {
            val intent = Intent(requireContext(), YouthSpaceWebViewActivity::class.java)
            intent.putExtra("homepage_url", requireArguments().getString("homepage"))
            startActivity(intent)
        }
    }
}