package com.alisayar.kitapligimuygulamas_proje2.post

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.alisayar.kitapligimuygulamas_proje2.R
import com.alisayar.kitapligimuygulamas_proje2.databinding.FragmentPostBinding


class PostFragment : Fragment() {

    private lateinit var binding: FragmentPostBinding
    private lateinit var viewModelFactory: PostViewModelFactory
    private lateinit var viewModel: PostViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_post, container, false)



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val argument = PostFragmentArgs.fromBundle(requireArguments())
        binding.postModel = argument.postModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.postBook.setOnClickListener {

            val action = argument.postModel.bookModel?.id?.let { it1 ->
                PostFragmentDirections.actionPostFragmentToBookDetailFragment(
                    it1
                )
            }
            if (action != null) {
                findNavController().navigate(action)
            }
        }
    }


}