package com.alisayar.kitapligimuygulamas_proje2.profile.reads

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class ReadsViewModelFactory(private val userId: String): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReadsViewModel::class.java)){
            return ReadsViewModel(userId) as T
        }
        throw IllegalArgumentException("Bilinmeyen ViewModel class")
    }

}