package com.alisayar.kitapligimuygulamas_proje2.profile.edit

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

class EditProfileViewModel: ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val storage: FirebaseStorage = FirebaseStorage.getInstance()

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> get() = _username

    private val _ppUrl = MutableLiveData<String>()
    val ppUrl: LiveData<String> get() = _ppUrl

    private val _bioText = MutableLiveData<String>()
    val bioText: LiveData<String> get() = _bioText

    val newUsername = MutableLiveData<String>()
    val newBioText = MutableLiveData<String>()
    private val newPpUrlByteArray = MutableLiveData<ByteArray>()

    private val _updatedProfileEvent = MutableLiveData<Boolean>()
    val updatedProfileEvent: LiveData<Boolean> get() = _updatedProfileEvent

    init {
        getProfileData()
    }


    private fun getProfileData(){
        if(auth.currentUser != null){
            viewModelScope.launch(Dispatchers.IO) {
                val userDoc = firestore.collection("Users").document(auth.currentUser!!.uid).get().await()
                withContext(Dispatchers.Main){
                    _username.value = userDoc["username"].toString()
                    _ppUrl.value = userDoc["ppUrl"].toString()
                    _bioText.value = userDoc["bioText"].toString()
                }
            }
        }
    }

    fun saveButton(){

        if(newUsername.value?.isNotEmpty() == true){

            auth.currentUser?.let{
                val reference = storage.reference
                val imageReference =reference.child("profilePictures")
                    .child("${auth.currentUser!!.uid}.jpg")
                viewModelScope.launch(Dispatchers.IO) {
                    newPpUrlByteArray.value?.let {
                        imageReference.putBytes(newPpUrlByteArray.value!!).addOnSuccessListener {
                            val uploadedImageReference = reference.child("profilePictures")
                                .child("${auth.currentUser!!.uid}.jpg")
                            uploadedImageReference.downloadUrl.addOnSuccessListener {
                                _ppUrl.value = it.toString()
                                firestore.collection("Users").document(auth.currentUser!!.uid).update("ppUrl", _ppUrl.value)
                            }
                        }
                    }
                    firestore.collection("Users").document(auth.currentUser!!.uid).update("username", newUsername.value, "bioText", newBioText.value).await()
                }
                _updatedProfileEvent.value = true
            }
        }

    }

    fun chosenImage(bitmap: Bitmap){
        newPpUrlByteArray.value = getImageByteArray(bitmap)
    }

    private fun getImageByteArray(bitmap: Bitmap) : ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }

    fun updatedProfileEventComplete(){
        _updatedProfileEvent.value = false
    }
}