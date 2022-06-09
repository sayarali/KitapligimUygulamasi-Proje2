package com.alisayar.kitapligimuygulamas_proje2.addpost

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.alisayar.kitapligimuygulamas_proje2.R
import com.alisayar.kitapligimuygulamas_proje2.databinding.FragmentAddBinding
import com.alisayar.kitapligimuygulamas_proje2.databinding.FragmentAddPostBinding


class AddPostFragment : Fragment() {

    private lateinit var binding: FragmentAddPostBinding
    private lateinit var viewModel: AddPostViewModel
    private lateinit var viewModelFactory: AddPostViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_post, container, false)
        val arguments = AddPostFragmentArgs.fromBundle(requireArguments())
        viewModelFactory = AddPostViewModelFactory(arguments.bookId)
        viewModel = ViewModelProvider(this, viewModelFactory)[AddPostViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.bookDetail.observe(viewLifecycleOwner, Observer {
            if(it != null){
                binding.model = it
            }
        })

        viewModel.toastMessage.observe(viewLifecycleOwner, Observer {
            if(it.isNotEmpty()){
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                viewModel.toastMessageComplete()
            }
        })

        viewModel.successEvent.observe(viewLifecycleOwner, Observer {
            if(it){
                val action = AddPostFragmentDirections.actionAddPostFragmentToHomeFragment()
                findNavController().navigate(action)
                viewModel.successEventComplete()
            }
        })

    }

}