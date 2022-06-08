package com.alisayar.kitapligimuygulamas_proje2.model

import com.alisayar.kitapligimuygulamas_proje2.network.Item
import com.google.firebase.Timestamp


data class PostModel(
    val bookModel: Item?,
    val userId: String,
    val rating: Float?,
    val comment: String?,
    val time: Timestamp
)
