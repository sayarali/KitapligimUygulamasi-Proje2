package com.alisayar.kitapligimuygulamas_proje2.home

import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.alisayar.kitapligimuygulamas_proje2.R
import com.alisayar.kitapligimuygulamas_proje2.databinding.FragmentHomeBinding
import com.alisayar.kitapligimuygulamas_proje2.discover.DiscoverFragmentRecyclerAdapter
import com.alisayar.kitapligimuygulamas_proje2.discover.OnClickListener
import com.alisayar.kitapligimuygulamas_proje2.discover.UserClickListener


class HomeFragment : Fragment() {


    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.homeRecyclerView.adapter = DiscoverFragmentRecyclerAdapter(OnClickListener {

        }, UserClickListener {
            viewModel.getUserId(it)
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.homeSwipeRefresh.setOnRefreshListener {
            viewModel.getPostData()
        }



        viewModel.goAddFragmentEvent.observe(viewLifecycleOwner, Observer {
            if (it){
                val action = HomeFragmentDirections.actionHomeFragmentToAddFragment()
                findNavController().navigate(action)
                viewModel.goAddFragmentComplete()
            }
        })

        viewModel.isRefreshing.observe(viewLifecycleOwner, Observer {
            binding.homeSwipeRefresh.isRefreshing = it
        })

        viewModel.userId.observe(viewLifecycleOwner, Observer {
            if(it != null){
                val action = HomeFragmentDirections.actionHomeFragmentToProfileFragment(it)
                findNavController().navigate(action)
                viewModel.completeNavigateProfile()
            }
        })

    }


}