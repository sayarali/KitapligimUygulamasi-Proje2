package com.alisayar.kitapligimuygulamas_proje2.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alisayar.kitapligimuygulamas_proje2.model.PostModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PostViewModel(private val postModel: PostModel): ViewModel(){

    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _postModelLive = MutableLiveData<PostModel>()
    val postModelLive: LiveData<PostModel> get() = _postModelLive

    init {
        _postModelLive.value = postModel
    }

    fun deletePost(){
        viewModelScope.launch(Dispatchers.IO) {
            firestore.collection("Posts").document(postModel.postId).delete().await()
        }
    }
}