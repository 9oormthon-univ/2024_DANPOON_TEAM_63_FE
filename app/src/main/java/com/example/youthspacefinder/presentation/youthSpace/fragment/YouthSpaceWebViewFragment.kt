package com.example.youthspacefinder.presentation.youthSpace.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.youthspacefinder.R
import com.example.youthspacefinder.databinding.FragmentYouthSpaceWebViewBinding
import com.example.youthspacefinder.presentation.youthSpace.viewmodel.YouthSpaceViewModel

class YouthSpaceWebViewFragment : Fragment() {

    private val binding by lazy { FragmentYouthSpaceWebViewBinding.inflate(layoutInflater) }
    val viewModel: YouthSpaceViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val youthSpaceUrl = viewModel.homepageUrl
        binding.webView.loadUrl(youthSpaceUrl!!)
    }

}