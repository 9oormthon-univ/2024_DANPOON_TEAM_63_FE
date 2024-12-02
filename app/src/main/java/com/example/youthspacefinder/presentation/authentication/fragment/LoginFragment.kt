package com.example.youthspacefinder.presentation.authentication.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.youthspacefinder.R
import com.example.youthspacefinder.databinding.FragmentLoginBinding
import com.example.youthspacefinder.presentation.authentication.viewmodel.AuthenticationViewModel

class LoginFragment : Fragment() {

    val binding by lazy { FragmentLoginBinding.inflate(layoutInflater) }
    val viewModel: AuthenticationViewModel by activityViewModels()

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
        val id = viewModel.id
        val password = viewModel.password
        // UX 관점에서 더 좋을 것 같음
        if(id.isNotBlank() && password.isNotBlank()) {
            binding.etId.setText(id, TextView.BufferType.EDITABLE)
            binding.etPassword.setText(password, TextView.BufferType.EDITABLE)
        }
    }

    private fun setupListeners() {
        binding.tvSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }
        binding.btnLogin.setOnClickListener {
            val id = binding.etId.text.toString()
            val password = binding.etPassword.text.toString()
            if(id.isBlank() || password.isBlank()) {
                Toast.makeText(requireContext(), "아직 입력하지 않은 정보가 있습니다!", Toast.LENGTH_SHORT).show()
            } else {
                // retrofit 서버 통신 + POST
                Toast.makeText(requireContext(), "환영합니다!", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_loginFragment_to_youthSpaceListFragment2)
            }
        }
    }

}