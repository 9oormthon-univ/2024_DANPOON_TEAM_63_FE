package com.example.youthspacefinder.presentation.surroundings

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.addCallback
import com.example.youthspacefinder.R
import com.example.youthspacefinder.databinding.FragmentAmenityWebViewBinding
import com.example.youthspacefinder.presentation.MainActivity

class AmenityWebViewFragment : Fragment() {

    val binding by lazy { FragmentAmenityWebViewBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val amenityUrl = requireArguments().getString("amenity_url")
        binding.webView.loadUrl(amenityUrl!!)
        goBack()
    }

    private fun goBack() {
        binding.webView.setOnKeyListener { v, keyCode, event ->
            // 이 필터는 액션이 ACTION_DOWN인지 확인합니다.
            if (event.action != KeyEvent.ACTION_DOWN) {
                return@setOnKeyListener true
            }
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (binding.webView.canGoBack()) {
                    binding.webView.goBack()
                } else {
                    (activity as? MainActivity)?.onBackPressed()
                }
                return@setOnKeyListener true
            }
            false
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (binding.webView.canGoBack()) {
                binding.webView.goBack()
            } else {
                Log.d("can't go back", "true")
                isEnabled = false
                requireActivity().onBackPressed()
            }
        }
    }

}