package com.alisayar.kitapligimuygulamas_proje2.bookdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alisayar.kitapligimuygulamas_proje2.R
import com.alisayar.kitapligimuygulamas_proje2.databinding.FragmentBookDetailBinding


class BookDetailFragment : Fragment() {

    private lateinit var binding: FragmentBookDetailBinding
    private lateinit var viewModel: BookDetailViewModel
    private lateinit var viewModelFactory: BookDetailViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_book_detail, container, false)

        val arguments = BookDetailFragmentArgs.fromBundle(requireArguments())
        viewModelFactory = BookDetailViewModelFactory(arguments.bookId)
        viewModel = ViewModelProvider(this, viewModelFactory)[BookDetailViewModel::class.java]


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.bookDetail.observe(viewLifecycleOwner, Observer {
            if(it != null){
                binding.model = it
            }
        })
    }



}