package com.alisayar.kitapligimuygulamas_proje2.profile.edit

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.alisayar.kitapligimuygulamas_proje2.R
import com.alisayar.kitapligimuygulamas_proje2.databinding.FragmentEditProfileBinding


class EditProfileFragment : Fragment() {

    private lateinit var binding: FragmentEditProfileBinding
    private lateinit var viewModel: EditProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_profile, container, false)
        viewModel = ViewModelProvider(this)[EditProfileViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.username.observe(viewLifecycleOwner, Observer {
            binding.editUsernameEt.setText(it)
        })
        viewModel.bioText.observe(viewLifecycleOwner, Observer {
            binding.editBioEt.setText(it)
        })

        binding.editChangeImage.setOnClickListener {
            requestPermission()
        }

        viewModel.updatedProfileEvent.observe(viewLifecycleOwner, Observer {
            if(it){
                findNavController().popBackStack()
            }
        })
    }

    private fun requestPermission(){
        if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        } else {
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, 2)
        }
    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 1){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galleryIntent, 2)
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 2){
            if(resultCode == Activity.RESULT_OK && data != null){

                val chosenUri = data.data
                var chosenBitmap: Bitmap
                chosenUri?.let {

                    if(Build.VERSION.SDK_INT >= 28){
                        val source = ImageDecoder.createSource(requireContext().contentResolver, chosenUri)
                        chosenBitmap = ImageDecoder.decodeBitmap(source)
                        binding.editImage.setImageBitmap(chosenBitmap)
                        viewModel.chosenImage(chosenBitmap)
                    } else {
                        chosenBitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, chosenUri)
                        binding.editImage.setImageBitmap(chosenBitmap)
                        viewModel.chosenImage(chosenBitmap)
                    }

                }

            }
        }
    }


}