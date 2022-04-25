package com.alisayar.kitapligimuygulamas_proje2.login.signin

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

private lateinit var auth: FirebaseAuth
class SignInViewModel: ViewModel() {

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()


    init {
        auth = FirebaseAuth.getInstance()
    }

    fun signIn() {
        auth.signInWithEmailAndPassword(email.value.toString(), password.value.toString()).addOnCompleteListener {
            if (it.isSuccessful){
                println("Giriş Yapıldı")
            }
        }.addOnFailureListener {
            println(it.localizedMessage)
        }
    }

}