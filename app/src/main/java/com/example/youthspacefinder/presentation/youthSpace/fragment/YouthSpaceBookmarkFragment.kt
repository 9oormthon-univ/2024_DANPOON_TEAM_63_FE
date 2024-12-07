package com.example.youthspacefinder.presentation.youthSpace.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.youthspacefinder.R
import com.example.youthspacefinder.databinding.FragmentYouthSpaceBookmarkBinding
import com.example.youthspacefinder.presentation.youthSpace.adapter.YouthSpaceListAdapter
import com.example.youthspacefinder.presentation.youthSpace.viewmodel.YouthSpaceFavoritesViewModel
import com.example.youthspacefinder.presentation.youthSpace.viewmodel.YouthSpaceInfoViewModel

class YouthSpaceBookmarkFragment : Fragment() {

    val binding by lazy { FragmentYouthSpaceBookmarkBinding.inflate(layoutInflater)}
    val youthSpaceFavoritesViewModel: YouthSpaceFavoritesViewModel by activityViewModels()
    val youthSpaceInfoViewModel: YouthSpaceInfoViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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
        if(youthSpaceFavoritesViewModel.userFavoriteSpaceIds.isEmpty()) {
            binding.recyclerview.visibility = View.GONE
            binding.llBookmarkEmpty.visibility = View.VISIBLE
        } else {
            binding.llBookmarkEmpty.visibility = View.GONE
            binding.recyclerview.adapter = YouthSpaceListAdapter(
                youthSpaceItems = youthSpaceFavoritesViewModel.userFavoriteSpaces.toList(),
                context = requireContext(),
                youthSpaceInfoViewModel =  youthSpaceInfoViewModel,
                youthSpaceFavoritesViewModel = youthSpaceFavoritesViewModel,
                startFragmentTag = "YouthSpaceBookmarkFragment"
            )
            binding.recyclerview.visibility = View.VISIBLE
        }
    }

    private fun setupListeners() {
        binding.ivGoBack.setOnClickListener {
            findNavController().navigate(R.id.action_youthSpaceBookmarkFragment_to_youthSpaceListFragment)
        }
    }

}