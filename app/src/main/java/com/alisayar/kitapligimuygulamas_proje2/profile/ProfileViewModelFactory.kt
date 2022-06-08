package com.alisayar.kitapligimuygulamas_proje2.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class ProfileViewModelFactory(private val userId: String?): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ProfileViewModel::class.java)){
            return ProfileViewModel(userId) as T
        }
        throw IllegalArgumentException("Bilinmeyen ViewModel class")
    }
}