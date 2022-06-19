package com.alisayar.kitapligimuygulamas_proje2.profile.tobereads

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.alisayar.kitapligimuygulamas_proje2.R
import com.alisayar.kitapligimuygulamas_proje2.databinding.FragmentToBeReadsBinding
import com.alisayar.kitapligimuygulamas_proje2.profile.reads.DeleteClickListener
import com.alisayar.kitapligimuygulamas_proje2.profile.reads.OnClickListener
import com.alisayar.kitapligimuygulamas_proje2.profile.reads.ReadsRecyclerAdapter


class ToBeReadsFragment : Fragment() {

    private lateinit var binding: FragmentToBeReadsBinding
    private lateinit var viewModel: ToBeReadsViewModel
    private lateinit var viewModelFactory: ToBeReadsViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_to_be_reads, container, false)
        val argument = ToBeReadsFragmentArgs.fromBundle(requireArguments())
        viewModelFactory = ToBeReadsViewModelFactory(argument.userId)
        viewModel = ViewModelProvider(this, viewModelFactory)[ToBeReadsViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.toBeReadsRecyclerView.adapter = ReadsRecyclerAdapter(OnClickListener{
            val action = it?.let { it1 ->
                ToBeReadsFragmentDirections.actionToBeReadsFragmentToBookDetailFragment(
                    it1
                )
            }
            if (action != null) {
                findNavController().navigate(action)
            }
        }, DeleteClickListener {
            if (it != null) {
                viewModel.deleteBookFromToBeReads(it)
                viewModel.getBooksList(argument.userId)
            }
        }, argument.userId)
        return binding.root
    }

}