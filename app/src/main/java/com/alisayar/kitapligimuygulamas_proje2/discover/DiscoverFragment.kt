package com.alisayar.kitapligimuygulamas_proje2.discover

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.alisayar.kitapligimuygulamas_proje2.R
import com.alisayar.kitapligimuygulamas_proje2.convertTimeFromTimestamp
import com.alisayar.kitapligimuygulamas_proje2.databinding.FragmentDiscoverBinding
import com.google.firebase.Timestamp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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
        binding.lifecycleOwner = viewLifecycleOwner
        binding.discoverRecylerView.adapter = DiscoverFragmentRecyclerAdapter(OnClickListener {
            val action = DiscoverFragmentDirections.actionDiscoverFragmentToPostFragment(it)
            findNavController().navigate(action)
        }, UserClickListener {
            viewModel.getUserId(it)
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.userId.observe(viewLifecycleOwner, Observer {
            if(it != null){
                val action = DiscoverFragmentDirections.actionDiscoverFragmentToProfileFragment(it)
                findNavController().navigate(action)
                viewModel.completeNavigateProfile()
            }
        })



        binding.discoverSwipeRefresh.setOnRefreshListener {
            viewModel.getPostDataFirebase()
        }

        viewModel.isRefreshing.observe(viewLifecycleOwner, Observer {
            binding.discoverSwipeRefresh.isRefreshing = it
        })

    }


}