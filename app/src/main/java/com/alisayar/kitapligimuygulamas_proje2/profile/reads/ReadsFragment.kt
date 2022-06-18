package com.alisayar.kitapligimuygulamas_proje2.profile.reads

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.alisayar.kitapligimuygulamas_proje2.R
import com.alisayar.kitapligimuygulamas_proje2.databinding.FragmentReadsBinding


class ReadsFragment : Fragment() {

    private lateinit var binding: FragmentReadsBinding
    private lateinit var viewModel: ReadsViewModel
    private lateinit var viewModelFactory: ReadsViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_reads, container, false)
        val argument = ReadsFragmentArgs.fromBundle(requireArguments())
        viewModelFactory = ReadsViewModelFactory(argument.userId)
        viewModel = ViewModelProvider(this, viewModelFactory)[ReadsViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.readsRecyclerView.adapter = ReadsRecyclerAdapter(OnClickListener {
            val action = it?.let { it1 ->
                ReadsFragmentDirections.actionReadsFragmentToBookDetailFragment(
                    it1
                )
            }
            if (action != null) {
                findNavController().navigate(action)
            }
        }, DeleteClickListener {
            if (it != null) {
                viewModel.deleteBookFromReads(it)
                viewModel.getBooksList(argument.userId)
            }
        }, argument.userId)

        return binding.root
    }

}