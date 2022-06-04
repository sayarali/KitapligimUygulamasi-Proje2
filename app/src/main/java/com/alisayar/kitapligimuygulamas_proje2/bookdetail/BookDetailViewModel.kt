package com.alisayar.kitapligimuygulamas_proje2.bookdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alisayar.kitapligimuygulamas_proje2.network.BooksApi
import com.alisayar.kitapligimuygulamas_proje2.network.Item
import kotlinx.coroutines.launch
import java.lang.Exception

class BookDetailViewModel(private val bookId: String) : ViewModel(){

    private val _bookDetail = MutableLiveData<Item?>()
    val bookDetail: LiveData<Item?> get() = _bookDetail

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

}