package com.alisayar.kitapligimuygulamas_proje2.login.signup

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
import com.alisayar.kitapligimuygulamas_proje2.MainActivity
import com.alisayar.kitapligimuygulamas_proje2.R
import com.alisayar.kitapligimuygulamas_proje2.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var viewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        viewModel = ViewModelProvider(this)[SignUpViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signupBtn.setOnClickListener {
            viewModel.createUser()
        }

        viewModel.usernameError.observe(viewLifecycleOwner, Observer {
            binding.signupUsernameTil.error = it
        })
        viewModel.emailError.observe(viewLifecycleOwner, Observer {
            binding.signupEmailTil.error = it
        })
        viewModel.passwordError.observe(viewLifecycleOwner, Observer {
            binding.signupPasswordTil.error = it
        })
        viewModel.passwordVerifyError.observe(viewLifecycleOwner, Observer {
            binding.signupPasswordVerifyTil.error = it
        })

        viewModel.toastMessage.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        })

        viewModel.successRegisterEvent.observe(viewLifecycleOwner, Observer {
            if(it){
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
                activity?.finish()
                viewModel.registerEventComplete()
            }
        })
    }

}