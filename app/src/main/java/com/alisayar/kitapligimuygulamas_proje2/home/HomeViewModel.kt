package com.alisayar.kitapligimuygulamas_proje2.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alisayar.kitapligimuygulamas_proje2.model.PostModel
import com.alisayar.kitapligimuygulamas_proje2.model.UserModel
import com.alisayar.kitapligimuygulamas_proje2.network.BookModel
import com.alisayar.kitapligimuygulamas_proje2.network.BooksApi
import com.alisayar.kitapligimuygulamas_proje2.network.Item
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class HomeViewModel: ViewModel() {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()


    private val _goAddFragmentEvent = MutableLiveData<Boolean>()
    val goAddFragmentEvent: LiveData<Boolean> get() = _goAddFragmentEvent

    private val _postList = MutableLiveData<List<PostModel>>()
    val postList: LiveData<List<PostModel>> get() = _postList

    private val _isRefreshing = MutableLiveData<Boolean>()
    val isRefreshing: LiveData<Boolean> get() = _isRefreshing

    private val _userId = MutableLiveData<String?>()
    val userId: LiveData<String?> get() = _userId
    init {
        getPostData()
    }


    fun getPostData(){
        _isRefreshing.value = true

        viewModelScope.launch(Dispatchers.IO) {

            val userQuery = firestore.collection("Users")
                .document(auth.currentUser!!.uid).collection("Following").get().await()




            val myPostQuery = firestore.collection("Posts")
                .whereEqualTo("userId", auth.currentUser!!.uid).get().await()

            val postListTemp = arrayListOf<PostModel>()
            for (userDoc in userQuery){

                val postQuery = firestore.collection("Posts").whereEqualTo("userId", userDoc["userId"]).get().await()

                for (postDoc in postQuery){
                    val postId = postDoc["postId"].toString()
                    val comment = postDoc["comment"].toString()
                    val rating = postDoc["rating"].toString()
                    val time = postDoc["time"] as Timestamp
                    val bookModel = BooksApi.retrofitService.getBookDetails(postDoc["bookId"].toString())
                    val userDocS = firestore.collection("Users").document(postDoc["userId"].toString()).get().await()
                    val userModel = UserModel(userDocS["id"].toString(), userDocS["username"].toString(), userDocS["email"].toString(), userDocS["bioText"].toString(), userDocS["ppUrl"].toString())
                    val postModel = PostModel(postId, bookModel, userModel, rating.toFloatOrNull(), comment, time)

                    postListTemp.add(postModel)







                }
            }
            withContext(Dispatchers.Main){
                postListTemp.sortByDescending {
                    it.time
                }
                _postList.value = postListTemp
                _isRefreshing.value = false
            }
        }
    }

    fun getUserId(userId: String?){
        if(userId != null){
            _userId.value = userId
        }
    }

    fun goAddFragment(){
        _goAddFragmentEvent.value = true
    }
    fun goAddFragmentComplete(){
        _goAddFragmentEvent.value = false
    }

    fun completeNavigateProfile(){
        _userId.value = null
    }
}