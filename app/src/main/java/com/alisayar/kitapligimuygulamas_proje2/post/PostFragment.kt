package com.alisayar.kitapligimuygulamas_proje2.post

import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.alisayar.kitapligimuygulamas_proje2.R
import com.alisayar.kitapligimuygulamas_proje2.databinding.FragmentPostBinding
import com.google.firebase.auth.FirebaseAuth


class PostFragment : Fragment() {

    private lateinit var binding: FragmentPostBinding
    private lateinit var viewModelFactory: PostViewModelFactory
    private lateinit var viewModel: PostViewModel
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_post, container, false)
        val argument = PostFragmentArgs.fromBundle(requireArguments())
        viewModelFactory = PostViewModelFactory(argument.postModel)
        viewModel = ViewModelProvider(this, viewModelFactory)[PostViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.postBook.setOnClickListener {
            val argument = PostFragmentArgs.fromBundle(requireArguments())
            val action = argument.postModel.bookModel?.id?.let { it1 ->
                PostFragmentDirections.actionPostFragmentToBookDetailFragment(
                    it1
                )
            }
            if (action != null) {
                findNavController().navigate(action)
            }
        }

        if(viewModel.postModelLive.value?.userModel?.id == auth.currentUser?.uid){
            setHasOptionsMenu(true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.post_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.delete_post -> {
                val alertDialog = AlertDialog.Builder(requireContext())
                    .setMessage("Gönderiyi silmek istediğinizden emin misiniz?")
                    .setPositiveButton("Evet", DialogInterface.OnClickListener { dialogInterface, i ->
                        viewModel.deletePost()
                        findNavController().popBackStack()
                    })
                    .setNegativeButton("Hayır", DialogInterface.OnClickListener { dialogInterface, i ->
                    })
                    .show()
            }
        }
        return super.onOptionsItemSelected(item)
    }


}