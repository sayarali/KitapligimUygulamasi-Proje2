package com.alisayar.kitapligimuygulamas_proje2.profile.reads

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alisayar.kitapligimuygulamas_proje2.network.BooksApi
import com.alisayar.kitapligimuygulamas_proje2.network.Item
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class ReadsViewModel(private val userId: String): ViewModel() {

    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _bookList = MutableLiveData<List<Item?>>()
    val bookList: LiveData<List<Item?>> get() = _bookList

    init {
        getBooksList(userId)
    }

    fun getBooksList(userId: String){

        viewModelScope.launch(Dispatchers.IO) {

            val readsQuery = firestore.collection("Users").document(userId).collection("Reads").get().await()
            readsQuery.sortedByDescending {
                it["time"].toString()
            }
            var tempList = listOf<Item>()
            for (readsDoc in readsQuery){
                tempList = tempList.plus(BooksApi.retrofitService.getBookDetails(readsDoc["bookId"].toString()))
               // _bookModel.value = _bookModel.value?.plus(BooksApi.retrofitService.getBookDetails(readsDoc["bookId"].toString())) ?: listOf()

            }
            withContext(Dispatchers.Main){
                _bookList.value = tempList
            }


        }

    }

    fun deleteBookFromReads(bookId: String){
        viewModelScope.launch(Dispatchers.IO) {

            firestore.collection("Users").document(userId).collection("Reads").document(bookId).delete().await()

        }
    }
}