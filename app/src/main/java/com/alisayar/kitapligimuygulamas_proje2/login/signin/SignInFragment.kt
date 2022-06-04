package com.alisayar.kitapligimuygulamas_proje2.login.signin

import android.content.Intent
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
import com.alisayar.kitapligimuygulamas_proje2.MainActivity
import com.alisayar.kitapligimuygulamas_proje2.R
import com.alisayar.kitapligimuygulamas_proje2.databinding.FragmentSignInBinding


class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private lateinit var viewModel: SignInViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false)
        viewModel = ViewModelProvider(this)[SignInViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signinBtn.setOnClickListener {
            viewModel.signIn()
        }


        binding.signinSignupBtn.setOnClickListener {
            val action = R.id.action_signInFragment_to_signUpFragment
            findNavController().navigate(action)
        }

        viewModel.emailError.observe(viewLifecycleOwner, Observer {
            binding.signinEmailTil.error = it
        })

        viewModel.passwordError.observe(viewLifecycleOwner, Observer {
            binding.signinPasswordTil.error = it
        })

        viewModel.loginSuccessEvent.observe(viewLifecycleOwner, Observer {
            if(it){
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
                activity?.finish()
                viewModel.loginEventComplete()
            }
        })

        viewModel.toastMessage.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        })
    }
}