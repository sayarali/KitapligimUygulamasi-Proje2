package com.alisayar.kitapligimuygulamas_proje2.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alisayar.kitapligimuygulamas_proje2.model.PostModel
import java.lang.IllegalArgumentException

class PostViewModelFactory(private val postModel: PostModel): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PostViewModel::class.java)){
            return PostViewModel(postModel) as T
        }
        throw IllegalArgumentException("Bilinmeyen ViewModel class")
    }


}