package dev_ali_hassan.app.marad.ui.homescreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev_ali_hassan.app.marad.R
import dev_ali_hassan.app.marad.databinding.FragmentHomeBinding

@AndroidEntryPoint
class HomeFragment: Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeScreenViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentHomeBinding.bind(view)

        val adapter = HomeItemsAdapter()
        binding.homeItemsRV.adapter = adapter
        adapter.submitList(viewModel.provideTempData())

    }

}