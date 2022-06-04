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

    val searchString = MutableLiveData<String>()

    fun getBooks(search: String){
        _booksList.value = ArrayList()
        viewModelScope.launch {
            try {
                _booksList.value = BooksApi.retrofitService.getBooksData(search).items
            } catch (e: Exception){
                println(e.localizedMessage)
            }
        }
    }

    init {

    }

}