package com.alisayar.kitapligimuygulamas_proje2.profile.tobereads

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alisayar.kitapligimuygulamas_proje2.network.BooksApi
import com.alisayar.kitapligimuygulamas_proje2.network.Item
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class ToBeReadsViewModel(private val userId: String): ViewModel() {

    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _bookList = MutableLiveData<List<Item?>>()
    val bookList: LiveData<List<Item?>> get() = _bookList

    init {
        getBooksList(userId)
    }

    fun getBooksList(userId: String){

        viewModelScope.launch(Dispatchers.IO) {

            val readsQuery = firestore.collection("Users").document(userId).collection("ToBeReads").orderBy("time", Query.Direction.DESCENDING).get().await()
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

    fun deleteBookFromToBeReads(bookId: String){
        viewModelScope.launch(Dispatchers.IO) {
            firestore.collection("Users").document(userId).collection("ToBeReads").document(bookId).delete().await()
        }
    }
}