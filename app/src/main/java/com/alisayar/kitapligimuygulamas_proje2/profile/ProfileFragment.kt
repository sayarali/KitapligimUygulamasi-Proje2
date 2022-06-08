package com.alisayar.kitapligimuygulamas_proje2.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.GridLayoutManager
import com.alisayar.kitapligimuygulamas_proje2.R
import com.alisayar.kitapligimuygulamas_proje2.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModelFactory: ProfileViewModelFactory
    private lateinit var viewModel: ProfileViewModel
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        auth = FirebaseAuth.getInstance()
        var userId = auth.currentUser!!.uid
        arguments?.let {
            if(it.get("userId") != null){
                userId = it.get("userId").toString()
            }
        }
        viewModelFactory = ProfileViewModelFactory(userId)
        viewModel = ViewModelProvider(this, viewModelFactory)[ProfileViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.profileRecyclerView.adapter = ProfileFragmentRecyclerAdapter()
        binding.profileRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.username.observe(viewLifecycleOwner, Observer {
            (activity as AppCompatActivity).supportActionBar?.title = it
        })
    }


}