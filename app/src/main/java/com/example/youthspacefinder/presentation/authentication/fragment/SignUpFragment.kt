package com.example.youthspacefinder.presentation.authentication.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.youthspacefinder.R
import com.example.youthspacefinder.databinding.FragmentSignUpBinding
import com.example.youthspacefinder.model.RegisterUserInfo
import com.example.youthspacefinder.network.RetrofitInstance
import com.example.youthspacefinder.presentation.authentication.viewmodel.AuthenticationViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpFragment : Fragment() {

    val binding by lazy { FragmentSignUpBinding.inflate(layoutInflater) }
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
        binding.btnSignup.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val nickname = binding.etNickname.text.toString()
            val password = binding.etPassword.text.toString()
            val email = binding.etEmail.text.toString()
            if (username.isBlank() || nickname.isBlank() || password.isBlank() || email.isBlank()) {
                Toast.makeText(requireContext(), "입력하지 않은 정보가 있습니다!", Toast.LENGTH_SHORT).show()
            } else {
                // retrofit 서버 통신 + POST
                val requestBody = RegisterUserInfo(
                    username = username,
                    nickname = nickname,
                    password = password,
                    email = email
                )
                RetrofitInstance.networkServiceBackEnd.registerUserInfo(requestBody).enqueue(object: Callback<Any> {
                    override fun onResponse(call: Call<Any>, response: Response<Any>) {
                        if(response.isSuccessful) {
                            Toast.makeText(requireContext(), "회원가입이 완료되었습니다!", Toast.LENGTH_SHORT).show()
                            authenticationViewModel.id = username
                            authenticationViewModel.password = password
                            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
                        } else {
                            // 여기에 걸림
                            Toast.makeText(requireContext(), "중복된 아이디가 있습니다!", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<Any>, t: Throwable) {
                        Toast.makeText(requireContext(), "네트워크 연결이 불안정합니다!", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }

}