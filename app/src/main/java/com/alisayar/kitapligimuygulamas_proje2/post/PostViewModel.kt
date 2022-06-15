package com.alisayar.kitapligimuygulamas_proje2.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alisayar.kitapligimuygulamas_proje2.model.PostModel

class PostViewModel(private val postModel: PostModel): ViewModel(){

    private val _postModelLive = MutableLiveData<PostModel>()
    val postModelLive: LiveData<PostModel> get() = _postModelLive

    init {
        _postModelLive.value = postModel
    }

}