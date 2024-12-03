package com.example.youthspacefinder.presentation.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.youthspacefinder.R
import com.example.youthspacefinder.databinding.FragmentSettingsUserLoggedOutBinding

class SettingsUserLoggedOutFragment : Fragment() {

    val binding by lazy { FragmentSettingsUserLoggedOutBinding.inflate(layoutInflater) }

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
        binding.ivGoBack.setOnClickListener {
            findNavController().navigate(R.id.action_settingsUserLoggedOutFragment_to_youthSpaceListFragment)
        }
        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_settingsUserLoggedOutFragment_to_loginFragment)
        }
    }
}