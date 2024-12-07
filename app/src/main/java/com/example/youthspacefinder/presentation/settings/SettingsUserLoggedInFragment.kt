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
import com.example.youthspacefinder.model.UserNicknameRequest
import com.example.youthspacefinder.model.UserPasswordRequest
import com.example.youthspacefinder.network.RetrofitInstance
import com.example.youthspacefinder.presentation.authentication.viewmodel.AuthenticationViewModel
import com.example.youthspacefinder.presentation.youthSpace.viewmodel.YouthSpaceFavoritesViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SettingsUserLoggedInFragment : Fragment() {

    val binding by lazy { FragmentSettingsUserLoggedInBinding.inflate(layoutInflater) }
    val authenticationViewModel: AuthenticationViewModel by activityViewModels()
    val youthSpaceFavoritesViewModel: YouthSpaceFavoritesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupListeners()
    }

    private fun setupViews() {
        binding.tvUserEmail.text = authenticationViewModel.email
    }

    private fun setupListeners() {
        binding.ivGoBack.setOnClickListener {
            findNavController().navigate(R.id.action_settingsUserLoggedInFragment_to_youthSpaceListFragment)
        }
        binding.btnSave.setOnClickListener {
            val newUserNickname = binding.etUserNickname.text.toString()
            if (newUserNickname.isBlank()) {
                Toast.makeText(requireContext(), "닉네임을 입력하지 않았습니다!", Toast.LENGTH_SHORT).show()
            } else {
                // 닉네임 변경 통신
                val userNicknameRequest = UserNicknameRequest(newUserNickname)
                RetrofitInstance.networkServiceBackEnd.changeUserNickname(
                    authenticationViewModel.userToken!!,
                    userNicknameRequest
                ).enqueue(object : Callback<Any> {
                    override fun onResponse(call: Call<Any>, response: Response<Any>) {
                        if (response.isSuccessful) {
                            Toast.makeText(requireContext(), "닉네임 변경을 완료했습니다!", Toast.LENGTH_SHORT)
                                .show()
                            authenticationViewModel.nickname = newUserNickname
                        }
                    }

                    override fun onFailure(call: Call<Any>, t: Throwable) {
                        Toast.makeText(requireContext(), "네트워크가 불안정합니다!", Toast.LENGTH_SHORT).show()
                    }

                })
            }
        }
        binding.btnChangePassword.setOnClickListener {
            val newPassword = binding.etNewPassword.text.toString()
            val newPasswordCheck = binding.etNewPasswordCheck.text.toString()
            val currentPassword = binding.etCurrentPassword.text.toString()
            if (newPassword.isBlank() || newPasswordCheck.isBlank() || currentPassword.isBlank()) {
                Toast.makeText(requireContext(), "입력하지 않은 정보가 있습니다!", Toast.LENGTH_SHORT).show()
            } else if (newPassword != newPasswordCheck) {
                Toast.makeText(requireContext(), "새 비밀번호가 일치하지 않습니다!", Toast.LENGTH_SHORT).show()
            } else if (currentPassword != authenticationViewModel.password) {
                Toast.makeText(requireContext(), "현재 비밀번호를 잘못 입력했습니다!", Toast.LENGTH_SHORT).show()
            } else {
                // 서버 통신
                val userPasswordRequest = UserPasswordRequest(
                    currentPassword = currentPassword,
                    newPassword = newPassword,
                    confirmNewPassword = newPasswordCheck
                )
                RetrofitInstance.networkServiceBackEnd.changeUserPassword(
                    authenticationViewModel.userToken!!,
                    userPasswordRequest
                ).enqueue(object: Callback<Any> {
                    override fun onResponse(call: Call<Any>, response: Response<Any>) {
                        if(response.isSuccessful) {
                            authenticationViewModel.password = newPassword
                            Toast.makeText(requireContext(), "비밀번호 변경을 완료했습니다!", Toast.LENGTH_SHORT).show()
                        } else {
                        }
                    }
                    override fun onFailure(call: Call<Any>, t: Throwable) {
                    }
                })
            }
        }
        binding.btnLogout.setOnClickListener {
            authenticationViewModel.isUserLoggedIn = false
            youthSpaceFavoritesViewModel.userFavoriteSpaceIds.clear()
            youthSpaceFavoritesViewModel.userFavoriteSpaces.clear()
            Toast.makeText(requireContext(), "로그아웃 되었습니다!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_settingsUserLoggedInFragment_to_youthSpaceListFragment)
        }
    }

}