package com.example.youthspacefinder.presentation.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.youthspacefinder.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    val binding by lazy { FragmentLoginBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

}