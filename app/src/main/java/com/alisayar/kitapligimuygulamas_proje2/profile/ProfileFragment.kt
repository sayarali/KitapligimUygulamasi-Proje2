package com.alisayar.kitapligimuygulamas_proje2.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.GridLayoutManager
import com.alisayar.kitapligimuygulamas_proje2.R
import com.alisayar.kitapligimuygulamas_proje2.databinding.FragmentProfileBinding
import com.alisayar.kitapligimuygulamas_proje2.login.LoginActivity
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

        viewModel.anyUserActive.observe(viewLifecycleOwner, Observer {
            if(it){
                binding.profileFollowButton.visibility = View.VISIBLE
            } else {
                binding.profileFollowButton.visibility = View.GONE
                setHasOptionsMenu(true)
            }
        })

        viewModel.isFollowing.observe(viewLifecycleOwner, Observer {
            if(it){
                binding.profileFollowButton.visibility = View.GONE
                binding.profileUnfollowButton.visibility = View.VISIBLE
            } else {
                binding.profileFollowButton.visibility = View.VISIBLE
                binding.profileUnfollowButton.visibility = View.GONE
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.profile_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.edit_profile_item -> {
                Toast.makeText(requireContext(), "Profili düzenleme", Toast.LENGTH_SHORT).show()
            }
            R.id.sign_out_item -> {
                auth.signOut()
                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }


}