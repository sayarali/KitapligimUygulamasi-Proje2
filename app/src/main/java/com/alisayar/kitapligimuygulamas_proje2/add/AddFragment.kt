package com.alisayar.kitapligimuygulamas_proje2.add

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.alisayar.kitapligimuygulamas_proje2.R
import com.alisayar.kitapligimuygulamas_proje2.databinding.FragmentAddBinding
import com.alisayar.kitapligimuygulamas_proje2.databinding.FragmentHomeBinding


class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding
    private lateinit var viewModel: AddViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add, container, false)
        viewModel = ViewModelProvider(this)[AddViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.addRecyclerView.adapter = AddFragmentRecyclerAdapter( OnClickListener {
            viewModel.getBookId(it)
        })
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)


        viewModel.searchString.observe(viewLifecycleOwner, Observer {
            viewModel.getBooks(it)
        })

        viewModel.bookId.observe(viewLifecycleOwner, Observer {
            if(it != null){
                val action = AddFragmentDirections.actionAddFragmentToBookDetailFragment(it)
                findNavController().navigate(action)
                viewModel.goFragmentDetailsComplete()
            }
        })



    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.barcode_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.barcode_item){
            TODO("Barkod tarama fonksiyonu")
        }
        return super.onOptionsItemSelected(item)
    }

}