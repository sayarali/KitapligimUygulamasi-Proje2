package com.alisayar.kitapligimuygulamas_proje2.addpost

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alisayar.kitapligimuygulamas_proje2.network.BooksApi
import com.alisayar.kitapligimuygulamas_proje2.network.Item
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*

class AddPostViewModel(private val bookId: String): ViewModel() {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _bookDetail = MutableLiveData<Item?>()
    val bookDetail: LiveData<Item?> get() = _bookDetail

    val rating = MutableLiveData<Float>()
    val comment = MutableLiveData<String>()

    private val _successEvent = MutableLiveData<Boolean>()
    val successEvent: LiveData<Boolean> get() = _successEvent

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> get() = _toastMessage

    init {
        getBookDetail(bookId)
    }

    private fun getBookDetail(bookId: String){
        viewModelScope.launch {
            try {
                _bookDetail.value = BooksApi.retrofitService.getBookDetails(bookId)
            } catch (e: Exception) {}
        }
    }

    fun addPostFirebase(bookId: String){
        auth.currentUser?.let {
            viewModelScope.launch {
                val time = Timestamp.now()
                val postHashMap = hashMapOf<String, Any>()
                val postId = UUID.randomUUID().toString()
                postHashMap["postId"] = postId
                postHashMap["userId"] = auth.currentUser!!.uid
                postHashMap["bookId"] = bookId
                postHashMap["time"] = time
                postHashMap["rating"] = rating
                postHashMap["comment"] = comment
                firestore.collection("Posts").document(postId).set(postHashMap).addOnCompleteListener {
                    if(it.isSuccessful){
                        _successEvent.value = true
                        _toastMessage.value = "Gönderi başarıyla yüklendi."
                    }
                }.addOnFailureListener {
                    _toastMessage.value = it.localizedMessage
                }
            }
        }
    }

    fun successEventComplete(){
        _successEvent.value = false
    }

    fun toastMessageComplete(){
        _toastMessage.value = ""
    }

}