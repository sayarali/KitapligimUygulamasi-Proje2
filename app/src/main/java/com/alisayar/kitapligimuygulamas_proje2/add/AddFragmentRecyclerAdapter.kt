package com.alisayar.kitapligimuygulamas_proje2.add

import android.content.ClipData
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alisayar.kitapligimuygulamas_proje2.databinding.AddFragmentRecyclerRowBinding
import com.alisayar.kitapligimuygulamas_proje2.network.Item

class AddFragmentRecyclerAdapter: ListAdapter<Item, AddFragmentRecyclerAdapter.ViewHolder>(DiffUtilCallback) {
    class ViewHolder(private val binding: AddFragmentRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(bookModel: Item?){
            binding.model = bookModel
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AddFragmentRecyclerRowBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }


}

object DiffUtilCallback: DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }

}
