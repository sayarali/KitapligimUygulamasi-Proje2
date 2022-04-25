package com.alisayar.kitapligimuygulamas_proje2.login.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

private lateinit var auth: FirebaseAuth

class SignUpViewModel: ViewModel() {


    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()



    init {
        auth = FirebaseAuth.getInstance()

    }

    fun createUser(){
        println("tık")
        println(email.value.toString())
        println(password.value.toString())
        auth.createUserWithEmailAndPassword(email.value.toString(), password.value.toString()).addOnCompleteListener {
            if(it.isSuccessful){
                println(email.value)
                println(password.value)
            }
        }
    }




}