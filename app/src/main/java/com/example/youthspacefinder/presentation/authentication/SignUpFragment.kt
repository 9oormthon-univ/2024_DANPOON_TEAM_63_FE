package com.example.youthspacefinder.presentation.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.youthspacefinder.R
import com.example.youthspacefinder.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    val binding by lazy { FragmentSignUpBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

}