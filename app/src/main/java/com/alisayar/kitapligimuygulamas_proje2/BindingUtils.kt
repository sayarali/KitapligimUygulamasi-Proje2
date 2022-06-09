package com.alisayar.kitapligimuygulamas_proje2

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alisayar.kitapligimuygulamas_proje2.add.AddFragmentRecyclerAdapter
import com.alisayar.kitapligimuygulamas_proje2.discover.DiscoverFragmentRecyclerAdapter
import com.alisayar.kitapligimuygulamas_proje2.model.PostModel
import com.alisayar.kitapligimuygulamas_proje2.network.ImageLinks
import com.alisayar.kitapligimuygulamas_proje2.network.Item
import com.alisayar.kitapligimuygulamas_proje2.profile.ProfileFragmentRecyclerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import de.hdodenhof.circleimageview.CircleImageView

@BindingAdapter("booksData")
fun bindrecyclerView(recyclerView: RecyclerView, booksList: List<Item>?){
    val adapter = recyclerView.adapter as AddFragmentRecyclerAdapter
    adapter.submitList(booksList)
}

@BindingAdapter("postData")
fun bindProfileRecyclerView(recyclerView: RecyclerView, postList: List<PostModel>?){
    val adapter = recyclerView.adapter as ProfileFragmentRecyclerAdapter
    adapter.submitList(postList)
}

@BindingAdapter("discoverPostData")
fun bindDiscoverRecyclerView(recyclerView: RecyclerView, postList: List<PostModel>?){
    val adapter = recyclerView.adapter as DiscoverFragmentRecyclerAdapter
    adapter.submitList(postList)
}

@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView, imgUrl: ImageLinks?){
    if(imgUrl != null) {
        imgUrl.smallThumbnail?.let {
            val imgUri = imgUrl.smallThumbnail!!.toUri().buildUpon().scheme("https").build()
            Glide.with(imageView.context)
                .load(imgUri)
                .into(imageView)
        }
    } else {
        imageView.setImageResource(R.drawable.resimyok)
    }
}

@BindingAdapter("anyImageUrl")
fun bindImageAnyUrl(imageView: CircleImageView, imgUrl: String?){
    if(imgUrl != null){
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imageView.context)
            .load(imgUri)
            .into(imageView)
    }
}