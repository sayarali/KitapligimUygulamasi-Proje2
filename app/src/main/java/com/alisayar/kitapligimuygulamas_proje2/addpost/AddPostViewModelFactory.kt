package com.alisayar.kitapligimuygulamas_proje2.addpost

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class AddPostViewModelFactory(private val bookId: String): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddPostViewModel::class.java)){
            return AddPostViewModel(bookId) as T
        }
        throw IllegalArgumentException("Bilinmeyen ViewModel class")
    }
}