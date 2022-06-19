package com.alisayar.kitapligimuygulamas_proje2.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alisayar.kitapligimuygulamas_proje2.databinding.ProfileFragmentRecyclerRowBinding
import com.alisayar.kitapligimuygulamas_proje2.model.PostModel

class ProfileFragmentRecyclerAdapter(private val clickListener: OnClickListener): ListAdapter<PostModel, ProfileFragmentRecyclerAdapter.ViewHolder>(com.alisayar.kitapligimuygulamas_proje2.profile.DiffUtilCallback) {

    class ViewHolder(private val binding: ProfileFragmentRecyclerRowBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(postModel: PostModel?) {
            binding.model = postModel
            binding.executePendingBindings()
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ProfileFragmentRecyclerRowBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            clickListener.onClick(item)
        }
        holder.bind(item)
    }
}

class OnClickListener(val clickListener: (postModel: PostModel) -> Unit){
    fun onClick(postModel: PostModel) = clickListener(postModel)
}

object DiffUtilCallback: DiffUtil.ItemCallback<PostModel>() {
    override fun areItemsTheSame(oldItem: PostModel, newItem: PostModel): Boolean {
        return oldItem.time == newItem.time
    }

    override fun areContentsTheSame(oldItem: PostModel, newItem: PostModel): Boolean {
        return oldItem == newItem
    }
}