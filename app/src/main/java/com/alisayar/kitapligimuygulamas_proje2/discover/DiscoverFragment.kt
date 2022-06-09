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
        binding.discoverRecylerView.adapter = DiscoverFragmentRecyclerAdapter(OnClickListener {
            viewModel.getPostId(it)
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

        viewModel.postId.observe(viewLifecycleOwner, Observer {
            if(it != null){
                println("post fragmente gidiliyoooor $it")
            }
        })

    }


}