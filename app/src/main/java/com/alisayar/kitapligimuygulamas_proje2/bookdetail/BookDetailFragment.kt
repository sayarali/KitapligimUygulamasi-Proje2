package com.alisayar.kitapligimuygulamas_proje2.bookdetail

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.alisayar.kitapligimuygulamas_proje2.R
import com.alisayar.kitapligimuygulamas_proje2.databinding.FragmentBookDetailBinding


class BookDetailFragment : Fragment() {

    private lateinit var binding: FragmentBookDetailBinding
    private lateinit var viewModel: BookDetailViewModel
    private lateinit var viewModelFactory: BookDetailViewModelFactory

    //Animations Variable
    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_open_anim) }
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_close_anim) }
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(requireContext(), R.anim.from_bottom_anim) }
    private val toBottom: Animation by lazy { AnimationUtils.loadAnimation(requireContext(), R.anim.to_bottom_anim) }

    private var clicked = false

    private var bookId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_book_detail, container, false)

        val arguments = BookDetailFragmentArgs.fromBundle(requireArguments())
        viewModelFactory = BookDetailViewModelFactory(arguments.bookId)
        viewModel = ViewModelProvider(this, viewModelFactory)[BookDetailViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.bookDetail.observe(viewLifecycleOwner, Observer {
            if(it != null){
                binding.model = it
                bookId = it.id
                (activity as AppCompatActivity).supportActionBar?.title = it.volumeInfo?.title
            }
        })


        binding.detailFab.setOnClickListener {
            onFabClicked()
        }

        viewModel.isOnReadsEvent.observe(viewLifecycleOwner, Observer {
            if(it){
                Toast.makeText(requireContext(), "Bu kitap zaten okunanlar listenizde.", Toast.LENGTH_LONG).show()
                viewModel.isOnReadsComplete()
            }

        })

        viewModel.isOnToBeReadsEvent.observe(viewLifecycleOwner, Observer {
            if(it){
                Toast.makeText(requireContext(), "Bu kitap zaten okunacaklar listenizde.", Toast.LENGTH_LONG).show()
                viewModel.isOnToBeReadsComplete()
            }
        })

        viewModel.isSuccessAdded.observe(viewLifecycleOwner, Observer {
            if(it){
                Toast.makeText(requireContext(), "Başarıyla eklendi.", Toast.LENGTH_LONG).show()
                viewModel.successAddedComplete()
            }
        })


        viewModel.showAlertDialog.observe(viewLifecycleOwner, Observer {
            if(it){
                val alertDialogBuilder = AlertDialog.Builder(requireContext())
                alertDialogBuilder.setMessage("Kitap hakkındaki düşüncelerinizi diğer kullanıcılar ile paylaşmak ister misiniz?")
                alertDialogBuilder.setPositiveButton("Evet", DialogInterface.OnClickListener { dialogInterface, i ->
                    bookId?.let { id ->
                        val action = BookDetailFragmentDirections.actionBookDetailFragmentToAddPostFragment(id)
                        findNavController().navigate(action)
                    }
                })
                alertDialogBuilder.setNegativeButton("Hayır", DialogInterface.OnClickListener { dialogInterface, i ->

                })
                alertDialogBuilder.show()
                viewModel.showAlertDialogComplete()
            }
        })


    }

    private fun onFabClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        clicked = !clicked
    }

    private fun setVisibility(clicked: Boolean) {
        if(!clicked){
            binding.apply {
                detailReadsFab.visibility = View.VISIBLE
                detailTobereadFab.visibility = View.VISIBLE
                detailReadsFab.isClickable = true
                detailTobereadFab.isClickable = true
            }
        } else {
            binding.apply {
                detailReadsFab.visibility = View.INVISIBLE
                detailTobereadFab.visibility = View.INVISIBLE
                detailReadsFab.isClickable = false
                detailTobereadFab.isClickable = false
            }
        }
    }
    private fun setAnimation(clicked: Boolean) {
        if(!clicked){
            binding.apply {
                detailReadsFab.startAnimation(fromBottom)
                detailTobereadFab.startAnimation(fromBottom)
                detailFab.startAnimation(rotateOpen)
            }
        } else {
            binding.apply {
                detailReadsFab.startAnimation(toBottom)
                detailTobereadFab.startAnimation(toBottom)
                detailFab.startAnimation(rotateClose)
            }
        }
    }




}