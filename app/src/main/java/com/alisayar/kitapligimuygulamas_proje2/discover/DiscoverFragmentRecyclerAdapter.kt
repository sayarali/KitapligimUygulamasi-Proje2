package com.alisayar.kitapligimuygulamas_proje2.discover

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alisayar.kitapligimuygulamas_proje2.databinding.DiscoverFragmentRecylerRowBinding
import com.alisayar.kitapligimuygulamas_proje2.model.PostModel
import com.alisayar.kitapligimuygulamas_proje2.profile.DiffUtilCallback

class DiscoverFragmentRecyclerAdapter: ListAdapter<PostModel, DiscoverFragmentRecyclerAdapter.ViewHolder>(DiffUtilCallback) {
    class ViewHolder(val binding: DiscoverFragmentRecylerRowBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(postModel: PostModel?) {
            binding.postModel = postModel
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DiscoverFragmentRecylerRowBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}