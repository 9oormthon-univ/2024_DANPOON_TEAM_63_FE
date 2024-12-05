package com.example.youthspacefinder.presentation.youthSpace.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.youthspacefinder.databinding.DialogModifyReviewBinding

class ReviewModifyDialog: DialogFragment() {
    lateinit var binding: DialogModifyReviewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogModifyReviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnModifyReview.setOnClickListener {
            Toast.makeText(requireContext(), "수정", Toast.LENGTH_SHORT).show()
        }
        binding.btnCancel.setOnClickListener {
            Toast.makeText(requireContext(), "취소", Toast.LENGTH_SHORT).show()
            dismiss()
        }
    }

    companion object {
        const val TAG = "ReviewModifyDialog"
    }
}