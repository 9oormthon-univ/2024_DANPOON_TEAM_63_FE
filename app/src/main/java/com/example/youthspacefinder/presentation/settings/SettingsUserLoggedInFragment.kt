package com.example.youthspacefinder.presentation.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.youthspacefinder.R
import com.example.youthspacefinder.databinding.FragmentSettingsUserLoggedInBinding
import com.example.youthspacefinder.presentation.authentication.viewmodel.AuthenticationViewModel

class SettingsUserLoggedInFragment : Fragment() {

    val binding by lazy { FragmentSettingsUserLoggedInBinding.inflate(layoutInflater) }
    val authenticationViewModel: AuthenticationViewModel by activityViewModels()

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
            findNavController().navigate(R.id.action_settingsUserLoggedInFragment_to_youthSpaceListFragment)
        }
        binding.btnSave.setOnClickListener {
            // 닉네임 변경
            val newUserNickname = binding.etUserNickname.text.toString()
        }
        binding.btnLogout.setOnClickListener {
            authenticationViewModel.isUserLoggedIn = false
            Toast.makeText(requireContext(), "로그아웃 되었습니다!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_settingsUserLoggedInFragment_to_youthSpaceListFragment)
        }
    }

}