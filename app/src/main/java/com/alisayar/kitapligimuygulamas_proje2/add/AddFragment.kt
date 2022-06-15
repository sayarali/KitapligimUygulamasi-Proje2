package com.alisayar.kitapligimuygulamas_proje2.add

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.alisayar.kitapligimuygulamas_proje2.Capture
import com.alisayar.kitapligimuygulamas_proje2.R
import com.alisayar.kitapligimuygulamas_proje2.databinding.FragmentAddBinding
import com.google.zxing.integration.android.IntentIntegrator
import java.lang.Exception


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
        binding.lifecycleOwner = viewLifecycleOwner
        binding.addRecyclerView.adapter = AddFragmentRecyclerAdapter( OnClickListener {
            viewModel.getBookId(it)
        })
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)




        viewModel.bookId.observe(viewLifecycleOwner, Observer {
            if(it != null){
                val action = AddFragmentDirections.actionAddFragmentToBookDetailFragment(it)
                findNavController().navigate(action)
                viewModel.goFragmentDetailsComplete()
            }
        })

        binding.addTil.setEndIconOnClickListener {
            viewModel.getBooks()
        }



    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.barcode_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.barcode_item){

            barcodeScanner()

        }
        return super.onOptionsItemSelected(item)
    }



    private fun barcodeScanner(){
        val intentIntegrator = IntentIntegrator.forSupportFragment(this)
        intentIntegrator.setPrompt("Flaş aç -> Ses açma tuşu\n\nFlaş kapat -> Ses kapatma tuşu")
        intentIntegrator.setBeepEnabled(false)
        intentIntegrator.setOrientationLocked(true)
        intentIntegrator.captureActivity = Capture::class.java
        intentIntegrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == IntentIntegrator.REQUEST_CODE){
            if (resultCode == Activity.RESULT_OK && data != null){
                try {
                    val intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data)
                    if(intentResult != null){
                        binding.addEt.setText(intentResult.contents)
                    }
                } catch (e: Exception){}
            }
        }


    }
}