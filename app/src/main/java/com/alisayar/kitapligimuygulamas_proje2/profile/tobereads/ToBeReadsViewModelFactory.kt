package com.alisayar.kitapligimuygulamas_proje2.profile.tobereads

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class ToBeReadsViewModelFactory(private val userId: String): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ToBeReadsViewModel::class.java))
            return ToBeReadsViewModel(userId) as T
        throw IllegalArgumentException("Bilinmeyen ViewModel sınıfı")
    }

}