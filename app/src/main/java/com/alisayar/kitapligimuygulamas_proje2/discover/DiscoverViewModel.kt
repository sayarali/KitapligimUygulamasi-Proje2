package com.alisayar.kitapligimuygulamas_proje2.discover

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alisayar.kitapligimuygulamas_proje2.model.PostModel
import com.alisayar.kitapligimuygulamas_proje2.model.UserModel
import com.alisayar.kitapligimuygulamas_proje2.network.BooksApi
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch


class DiscoverViewModel: ViewModel() {

    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _postList = MutableLiveData<List<PostModel>?>()
    val postList: LiveData<List<PostModel>?> get() = _postList

    init {
        getPostDataFirebase()
    }

    private fun getPostDataFirebase(){

        viewModelScope.launch {
            val list = arrayListOf<PostModel>()
            firestore.collection("Posts").get().addOnSuccessListener {
                val documents = it.documents
                viewModelScope.launch {
                    for (document in documents){
                        lateinit var userModel: UserModel
                        firestore.collection("Users").document(document["userId"].toString()).get().addOnSuccessListener { doc ->
                            userModel = UserModel(doc["id"].toString(), doc["username"].toString(), doc["email"].toString(), doc["bioText"].toString(), doc["ppUrl"].toString())
                        }
                        val postId = document.id
                        val bookModel = BooksApi.retrofitService.getBookDetails(document["bookId"].toString())

                        val comment = document["comment"].toString()
                        val rating = document["rating"].toString()
                        val time = document["time"] as Timestamp
                        val postModel = PostModel(postId, bookModel, userModel, rating.toFloatOrNull(), comment, time)

                        list.add(postModel)
                    }
                    list.sortByDescending {
                        it.time
                    }
                    _postList.value = list
                }

            }
        }

    }
}