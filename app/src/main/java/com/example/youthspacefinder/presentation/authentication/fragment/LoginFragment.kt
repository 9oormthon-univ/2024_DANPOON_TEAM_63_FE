package com.example.youthspacefinder.presentation.authentication.fragment

import android.os.Bundle
import android.util.Log
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
import com.example.youthspacefinder.model.LoginUserInfo
import com.example.youthspacefinder.model.UserTokenResponse
import com.example.youthspacefinder.network.RetrofitInstance
import com.example.youthspacefinder.presentation.authentication.viewmodel.AuthenticationViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment() {

    val binding by lazy { FragmentLoginBinding.inflate(layoutInflater) }
    val authenticationViewModel: AuthenticationViewModel by activityViewModels()

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
        val id = authenticationViewModel.id
        val password = authenticationViewModel.password
        // UX 관점에서 더 좋을 것 같음
        if(id.isNotBlank() && password.isNotBlank()) {
            binding.etId.setText(id, TextView.BufferType.EDITABLE)
            binding.etPassword.setText(password, TextView.BufferType.EDITABLE)
        }
    }

    private fun setupListeners() {
        binding.btnSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }
        binding.btnLogin.setOnClickListener {
            val id = binding.etId.text.toString()
            val password = binding.etPassword.text.toString()
            if(id.isBlank() || password.isBlank()) {
                Toast.makeText(requireContext(), "아직 입력하지 않은 정보가 있습니다!", Toast.LENGTH_SHORT).show()
            } else {
                val loginInfo = LoginUserInfo(username = id, password = password)
                RetrofitInstance.networkServiceBackEnd.checkUserLoginInfo(loginInfo).enqueue(object:
                    Callback<UserTokenResponse> {
                    override fun onResponse(call: Call<UserTokenResponse>, response: Response<UserTokenResponse>) {
                        if(response.isSuccessful) {
                            val response = response.body()
                            val userToken = response!!.token
                            authenticationViewModel.id = id
                            authenticationViewModel.isUserLoggedIn = true
                            authenticationViewModel.userToken = "Bearer $userToken"
                            authenticationViewModel.password = password
                            authenticationViewModel.nickname = response.nickname
                            authenticationViewModel.email = response.email
                            Log.d("token", authenticationViewModel.userToken!!)
                            Toast.makeText(requireContext(), "환영합니다!", Toast.LENGTH_SHORT).show() // nickname 님 환영합니다 가 더 나을 수도?
                            findNavController().navigate(R.id.action_loginFragment_to_youthSpaceListFragment)
                        } else {
                            Toast.makeText(requireContext(), "해당 정보는 존재하지 않습니다!", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<UserTokenResponse>, t: Throwable) {
                        Toast.makeText(requireContext(), "네트워크 연결이 불안정합니다!", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }

}