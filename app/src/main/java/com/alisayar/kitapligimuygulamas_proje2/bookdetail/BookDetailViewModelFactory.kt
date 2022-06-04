package com.alisayar.kitapligimuygulamas_proje2.bookdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class BookDetailViewModelFactory(private val bookId: String): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(BookDetailViewModel::class.java)){
            return BookDetailViewModel(bookId) as T
        }
        throw IllegalArgumentException("Bilinmeyen ViewModel class")
    }

}