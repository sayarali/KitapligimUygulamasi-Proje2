package com.alisayar.kitapligimuygulamas_proje2.profile

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
import kotlinx.coroutines.launch
import java.lang.Exception

class ProfileViewModel(private val userId: String?) : ViewModel(){

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()


    private val _followUserId = MutableLiveData<String?>()
    val followUserId: LiveData<String?> get() = _followUserId

    private val _postList = MutableLiveData<List<PostModel>?>()
    val postList: LiveData<List<PostModel>?> get() = _postList

    private val _isFollowing = MutableLiveData<Boolean>()
    val isFollowing: LiveData<Boolean> get() = _isFollowing

    val username = MutableLiveData<String>()
    val ppUrl = MutableLiveData<String>()
    val bioText = MutableLiveData<String>()

    val toBeReads = MutableLiveData<Int>()
    val reads = MutableLiveData<Int>()
    val followers = MutableLiveData<Int>()
    val following = MutableLiveData<Int>()
    val postCount = MutableLiveData<Int>()

    private val _anyUserActive = MutableLiveData<Boolean>()
    val anyUserActive: LiveData<Boolean> get() = _anyUserActive

    init {
        getUserDataFirebase(userId)
        getPostDataFirebase(userId)
        _followUserId.value = userId
        isFollowingUser(userId)
        _anyUserActive.value = userId != auth.currentUser?.uid
    }
    private fun getUserDataFirebase(userId: String?){
        viewModelScope.launch {
            userId?.let {
                firestore.collection("Users").document(userId).addSnapshotListener { value, error ->
                    error?.printStackTrace()
                    if(value != null){
                        val data = value.data
                        username.value = data?.get("username").toString()
                        ppUrl.value = data?.get("ppUrl").toString()
                        bioText.value = data?.get("bioText").toString()
                    }
                }

                firestore.collection("Users").document(userId).collection("ToBeReads").get().addOnSuccessListener {
                    toBeReads.value = it.size()
                }
                firestore.collection("Users").document(userId).collection("Reads").get().addOnSuccessListener {
                    reads.value = it.size()
                }
                firestore.collection("Users").document(userId).collection("Followers").get().addOnSuccessListener {
                    followers.value = it.size()
                }
                firestore.collection("Users").document(userId).collection("Following").get().addOnSuccessListener {
                    following.value = it.size()
                }
            }
        }
    }

    private fun getPostDataFirebase(userId: String?){

        viewModelScope.launch {
            userId?.let {
                lateinit var userModel: UserModel
                firestore.collection("Users").document(userId).get().addOnSuccessListener {
                    userModel = UserModel(it["id"].toString(), it["username"].toString(), it["email"].toString(), it["bioText"].toString(), it["ppUrl"].toString())
                }

                val list = arrayListOf<PostModel>()
                firestore.collection("Posts").whereEqualTo("userId", userId).get().addOnSuccessListener {
                    postCount.value = it.size()
                    val documents = it.documents
                    viewModelScope.launch {
                        for (document in documents){
                            val postId = document["postId"].toString()
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

    private fun isFollowingUser(userId: String?){
        if(userId != null){
            viewModelScope.launch {
                firestore.collection("Users").document(userId).collection("Followers").get().addOnSuccessListener {
                    val documents = it.documents
                    for (document in documents){
                        if(document["userId"] == auth.currentUser!!.uid)
                            _isFollowing.value = true
                        break
                    }
                }
            }

        }
    }


    fun followUser(userId: String?){
        if(userId != null){
            viewModelScope.launch {
                val userIdHashMap = hashMapOf<String, Any>()
                userIdHashMap["userId"] = auth.currentUser!!.uid
                firestore.collection("Users").document(userId).collection("Followers").document(auth.currentUser!!.uid).set(userIdHashMap).addOnSuccessListener {
                    _isFollowing.value = true
                }
                val _userIdHashMap = hashMapOf<String, Any>()
                _userIdHashMap["userId"] = userId
                firestore.collection("Users").document(auth.currentUser!!.uid).collection("Following").document(userId).set(_userIdHashMap)
            }

        }
    }

    fun unFollowUser(userId: String?){
        if(userId != null){
            viewModelScope.launch {
                val userIdHashMap = hashMapOf<String, Any>()
                userIdHashMap["userId"] = auth.currentUser!!.uid
                firestore.collection("Users").document(userId).collection("Followers").document(auth.currentUser!!.uid).delete().addOnSuccessListener {
                    _isFollowing.value = false
                }
                val _userIdHashMap = hashMapOf<String, Any>()
                _userIdHashMap["userId"] = userId
                firestore.collection("Users").document(auth.currentUser!!.uid).collection("Following").document(userId).delete()
            }

        }
    }

}