package com.example.youthspacefinder.presentation.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.youthspacefinder.R
import com.example.youthspacefinder.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    val binding by lazy { FragmentSignUpBinding.inflate(layoutInflater) }
    val viewModel: AuthenticationViewModel by activityViewModels()

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
        binding.btnSignup.setOnClickListener {
            val nickname = binding.etNickname.text.toString()
            val id = binding.etId.text.toString()
            val password = binding.etPassword.text.toString()
            val email = binding.etEmail.text.toString()
            if(nickname.isBlank() || id.isBlank() || password.isBlank() || email.isBlank()) {
                Toast.makeText(requireContext(), "입력하지 않은 정보가 있습니다!", Toast.LENGTH_SHORT).show()
            } else {
                // retrofit 서버 통신 + POST
                // viewmodel 을 써야하나?
                Toast.makeText(requireContext(), "회원가입이 완료되었습니다!", Toast.LENGTH_SHORT).show()
                viewModel.id = id
                viewModel.password = password
                findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
            }
        }
    }

}