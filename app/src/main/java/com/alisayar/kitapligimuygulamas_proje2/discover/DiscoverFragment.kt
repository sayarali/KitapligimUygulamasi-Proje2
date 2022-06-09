package com.alisayar.kitapligimuygulamas_proje2.discover

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.alisayar.kitapligimuygulamas_proje2.R
import com.alisayar.kitapligimuygulamas_proje2.databinding.FragmentDiscoverBinding

class DiscoverFragment : Fragment() {


    private lateinit var binding: FragmentDiscoverBinding
    private lateinit var viewModel: DiscoverViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_discover, container, false)
        viewModel = ViewModelProvider(this)[DiscoverViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.discoverRecylerView.adapter = DiscoverFragmentRecyclerAdapter()
        return binding.root
    }


}