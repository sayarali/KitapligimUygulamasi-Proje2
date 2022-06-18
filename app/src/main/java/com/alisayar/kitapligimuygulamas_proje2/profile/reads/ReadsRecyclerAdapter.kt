package com.alisayar.kitapligimuygulamas_proje2.profile.reads

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alisayar.kitapligimuygulamas_proje2.add.DiffUtilCallback
import com.alisayar.kitapligimuygulamas_proje2.databinding.ReadsFragmentRecyclerRowBinding
import com.alisayar.kitapligimuygulamas_proje2.network.Item
import com.google.firebase.auth.FirebaseAuth

class ReadsRecyclerAdapter(private val onClickListener: OnClickListener, private val deleteClickListener: DeleteClickListener,private val userId: String): ListAdapter<Item, ReadsRecyclerAdapter.ViewHolder>(DiffUtilCallback) {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    class ViewHolder(val binding: ReadsFragmentRecyclerRowBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(bookModel: Item?) {
            binding.bookModel = bookModel
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ReadsFragmentRecyclerRowBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(item.id)
        }
        holder.binding.readsRecyclerDelete.setOnClickListener {
            deleteClickListener.onClick(item.id)
        }
        if(userId != auth.currentUser?.uid){
            holder.binding.readsRecyclerDelete.visibility = View.GONE
        } else {
            holder.binding.readsRecyclerDelete.visibility = View.VISIBLE
        }

        holder.bind(item)
    }


}

class OnClickListener(val clickListener: (bookId: String?) -> Unit){
    fun onClick(bookId: String?) = clickListener(bookId)
}

class DeleteClickListener(val clickListener: (bookId: String?) -> Unit){
    fun onClick(bookId: String?) = clickListener(bookId)
}

