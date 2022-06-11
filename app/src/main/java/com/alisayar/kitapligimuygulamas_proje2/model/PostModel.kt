package com.alisayar.kitapligimuygulamas_proje2.model

import android.os.Parcelable
import androidx.versionedparcelable.ParcelField
import com.alisayar.kitapligimuygulamas_proje2.network.Item
import com.google.firebase.Timestamp
import java.io.Serializable


data class PostModel(
    val postId: String,
    val bookModel: Item?,
    val userModel: UserModel,
    val rating: Float?,
    val comment: String?,
    val time: Timestamp
) : Serializable
