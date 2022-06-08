package com.alisayar.kitapligimuygulamas_proje2.login.signup

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alisayar.kitapligimuygulamas_proje2.login.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import java.lang.Exception

private lateinit var auth: FirebaseAuth
@SuppressLint("StaticFieldLeak")
private lateinit var firestore: FirebaseFirestore

class SignUpViewModel() : ViewModel(){


    val username = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val passwordVerify = MutableLiveData<String>()
    private val ppUrl = "https://firebasestorage.googleapis.com/v0/b/kitapligim-proje2.appspot.com/o/user.png?alt=media&token=c30b12f5-7120-4e73-a882-eeebab1ac758"
    private val bioText = ""

    private val _usernameError = MutableLiveData<String>()
    val usernameError: LiveData<String> get() = _usernameError
    private val _emailError = MutableLiveData<String>()
    val emailError: LiveData<String> get() = _emailError
    private val _passwordError = MutableLiveData<String>()
    val passwordError: LiveData<String> get() = _passwordError
    private val _passwordVerifyError = MutableLiveData<String>()
    val passwordVerifyError: LiveData<String> get() = _passwordVerifyError

    private val _toastMessage = MutableLiveData<String?>()
    val toastMessage: LiveData<String?> get() = _toastMessage

    private val _successRegisterEvent = MutableLiveData<Boolean>()
    val successRegisterEvent: LiveData<Boolean> get() = _successRegisterEvent

    init {
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
    }



    fun createUser(){

        var isError = false
        if(username.value.toString() == "" || username.value == null){
           _usernameError.value = "Kullanıcı adı boş bırakılamaz."
            isError = true
        } else
            _usernameError.value = ""
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


        if(password.value.toString() != passwordVerify.value.toString()){
            _passwordVerifyError.value = "Parolalar eşleşmiyor."
            isError = true
        } else
            _passwordVerifyError.value = ""

        if(isError){
            Log.e("SignUpViewModel", "EditText'lerden hatalı veri geliyor.")
        } else {

            val userModel = UserModel(username.value.toString(), email.value.toString(), password.value.toString(), ppUrl, bioText)
            viewModelScope.launch {
                try {
                    auth.createUserWithEmailAndPassword(userModel.email, userModel.password).addOnCompleteListener {
                        if(it.isSuccessful){
                            createUserCollection(userModel, auth.currentUser!!)
                        }
                    }.addOnFailureListener { e ->
                        _toastMessage.value = e.localizedMessage


                    }
                } catch (exc: Exception){}
            }
        }
    }

    private fun createUserCollection(userModel: UserModel, user: FirebaseUser){

        viewModelScope.launch {
            try {
                val userHashMap = hashMapOf<String, Any>()
                userHashMap["id"] = user.uid
                userHashMap["username"] = userModel.userName
                userHashMap["email"] = userModel.email
                userHashMap["ppUrl"] = userModel.ppUrl
                userHashMap["bioText"] = userModel.bioText
                firestore.collection("Users").document(user.uid).set(userHashMap).addOnCompleteListener {
                    _successRegisterEvent.value = true
                }.addOnFailureListener {
                    _toastMessage.value = it.localizedMessage
                }
            } catch (e: Exception){}
        }

    }

    fun registerEventComplete(){
        _successRegisterEvent.value = false
    }




}