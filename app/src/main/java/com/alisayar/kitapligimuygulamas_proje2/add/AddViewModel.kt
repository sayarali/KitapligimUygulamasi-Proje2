package com.alisayar.kitapligimuygulamas_proje2.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alisayar.kitapligimuygulamas_proje2.network.BooksApi
import com.alisayar.kitapligimuygulamas_proje2.network.Item
import kotlinx.coroutines.launch
import java.lang.Exception

class AddViewModel: ViewModel() {

    private val _booksList = MutableLiveData<List<Item>?>()
    val booksList: LiveData<List<Item>?> get() = _booksList

    private val _bookId = MutableLiveData<String?>()
    val bookId: LiveData<String?> get() = _bookId

    val searchString = MutableLiveData<String>()

    init {

    }

    fun getBooks(){
        _booksList.value = ArrayList()
        viewModelScope.launch {
            try {
                _booksList.value = BooksApi.retrofitService.getBooksData(searchString.value.toString()).items
            } catch (e: Exception){
                println(e.localizedMessage)
            }
        }
    }

    fun getBookId(bookId: String?){
        bookId?.let {
            _bookId.value = bookId
        }
    }

    fun goFragmentDetailsComplete() {
        _bookId.value = null
    }



}