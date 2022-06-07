package com.alisayar.kitapligimuygulamas_proje2.bookdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alisayar.kitapligimuygulamas_proje2.network.BookModel
import com.alisayar.kitapligimuygulamas_proje2.network.BooksApi
import com.alisayar.kitapligimuygulamas_proje2.network.Item
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import java.lang.Exception

class BookDetailViewModel(private val bookId: String) : ViewModel(){

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _bookDetail = MutableLiveData<Item?>()
    val bookDetail: LiveData<Item?> get() = _bookDetail

    private val _isOnReadsEvent = MutableLiveData<Boolean>()
    val isOnReadsEvent: LiveData<Boolean> get() = _isOnReadsEvent

    private val _isOnToBeReadsEvent = MutableLiveData<Boolean>()
    val isOnToBeReadsEvent: LiveData<Boolean> get() = _isOnToBeReadsEvent

    private val _isSuccessAdded = MutableLiveData<Boolean>()
    val isSuccessAdded: LiveData<Boolean> get() = _isSuccessAdded

    private val _showAlertDialogEvent = MutableLiveData<Boolean>()
    val showAlertDialog: LiveData<Boolean> get() = _showAlertDialogEvent

    init {
        getBookDetail(bookId)
    }

    private fun getBookDetail(bookId: String){
        viewModelScope.launch {
            try {
                _bookDetail.value = BooksApi.retrofitService.getBookDetails(bookId)
            } catch (e: Exception){

            }
        }
    }

    fun isOnReads(bookId: String){
        if(auth.currentUser != null){
            var inList = false
            viewModelScope.launch {
                firestore.collection("Users").document(auth.currentUser!!.uid).collection("Reads").get().addOnSuccessListener {
                    val documents = it.documents
                    //val documentList = arrayListOf<String>()
                    for (document in documents){
                        if(document.id == bookId){
                            _isOnReadsEvent.value = true
                            inList = true
                            break
                        }
                    }
                    if(!inList){
                        addToReads(bookId)
                        _showAlertDialogEvent.value = true
                    }
                }
            }

        }
    }

    private fun addToReads(bookId: String){
        viewModelScope.launch {
            val bookHashMap = hashMapOf<String, Any>()
            bookHashMap["bookId"] = bookId
            val time = Timestamp.now()
            bookHashMap["time"] = time
            firestore.collection("Users").document(auth.currentUser!!.uid).collection("Reads").document(bookId).set(bookHashMap).addOnCompleteListener {
                if(it.isSuccessful){
                    _isSuccessAdded.value = true
                    firestore.collection("Users").document(auth.currentUser!!.uid).collection("ToBeReads").document(bookId).delete()
                }
            }.addOnFailureListener {
                println(it.localizedMessage)
            }

        }
    }



    fun isOnReadsComplete(){
        _isOnReadsEvent.value = false
    }

    fun isOnToBeReads(bookId: String){
        if(auth.currentUser != null){
            var inList = false
            viewModelScope.launch {
                firestore.collection("Users").document(auth.currentUser!!.uid).collection("ToBeReads").get().addOnSuccessListener {
                    val documents = it.documents
                    //val documentList = arrayListOf<String>()
                    for (document in documents){
                        if(document.id == bookId){
                            _isOnToBeReadsEvent.value = true
                            inList = true
                            break
                        }
                    }
                    if(!inList){
                        addToBeReads(bookId)
                    }
                }
            }

        }
    }

    private fun addToBeReads(bookId: String){
        if(auth.currentUser != null){
            viewModelScope.launch {
                val bookHashMap = hashMapOf<String, Any>()
                bookHashMap["bookId"] = bookId
                val time = Timestamp.now()
                bookHashMap["time"] = time
                firestore.collection("Users").document(auth.currentUser!!.uid).collection("ToBeReads").document(bookId).set(bookHashMap).addOnCompleteListener {
                    if(it.isSuccessful){
                        _isSuccessAdded.value = true
                    }
                }.addOnFailureListener {
                    println(it.localizedMessage)
                }
            }
        }
    }

    fun isOnToBeReadsComplete() {
        _isOnToBeReadsEvent.value= false
    }


    fun successAddedComplete(){
        _isSuccessAdded.value = false
    }


    fun showAlertDialogComplete(){
        _showAlertDialogEvent.value = false
    }


}