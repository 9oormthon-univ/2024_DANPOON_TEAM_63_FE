package com.example.youthspacefinder.presentation.youthSpace

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.youthspacefinder.R
import com.example.youthspacefinder.databinding.FragmentYouthSpaceWebViewBinding

class YouthSpaceWebViewFragment : Fragment() {

    private val binding by lazy { FragmentYouthSpaceWebViewBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val youthSpaceUrl = requireArguments().getString("youth_space_url")
        binding.webView.loadUrl(youthSpaceUrl!!)
    }

}