package com.example.youthspacefinder.presentation.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.youthspacefinder.R
import com.example.youthspacefinder.databinding.FragmentSettingsUserInfoBinding

class SettingsUserInfoFragment : Fragment() {

    val binding by lazy { FragmentSettingsUserInfoBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

}