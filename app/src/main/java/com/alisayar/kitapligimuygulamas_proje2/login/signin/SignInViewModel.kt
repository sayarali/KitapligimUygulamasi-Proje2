package com.alisayar.kitapligimuygulamas_proje2.login.signin

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

private lateinit var auth: FirebaseAuth
class SignInViewModel: ViewModel() {

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private val _emailError = MutableLiveData<String>()
    val emailError: LiveData<String> get() = _emailError
    private val _passwordError = MutableLiveData<String>()
    val passwordError: LiveData<String> get() = _passwordError

    private val _loginSuccessEvent = MutableLiveData<Boolean>()
    val loginSuccessEvent: LiveData<Boolean> get() = _loginSuccessEvent

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> get() = _toastMessage

    init {
        auth = FirebaseAuth.getInstance()
    }

    fun signIn() {
        var isError = false
        if(email.value.toString() == "" || email.value == null){
            _emailError.value = "E-posta boş bırakılamaz."
            isError = true
        } else
            _emailError.value = ""
        if(password.value.toString() == "" || password.value == null){
            _passwordError.value = "Parola boş bırakılamaz."
            isError = true
        } else
            _passwordError.value = ""

        if(!isError){
            auth.signInWithEmailAndPassword(email.value.toString(), password.value.toString()).addOnCompleteListener {
                if (it.isSuccessful){
                    _loginSuccessEvent.value = true
                }
            }.addOnFailureListener {
                _toastMessage.value = it.localizedMessage
            }
        }

    }

    fun loginEventComplete(){
        _loginSuccessEvent.value = false
    }

}