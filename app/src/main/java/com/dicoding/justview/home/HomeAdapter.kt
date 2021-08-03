package com.dicoding.justview.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.justview.core.databinding.ViewItemBinding
import com.dicoding.justview.core.domain.model.View

class HomeAdapter : ListAdapter<View, HomeAdapter.ViewPropertyViewHolder>(DiffCallback) {

    var onItemClick: ((View) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewPropertyViewHolder {
        return ViewPropertyViewHolder(ViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewPropertyViewHolder, position: Int) {
        val viewProperty = getItem(position)
        holder.bind(viewProperty)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<View>() {
        override fun areItemsTheSame(oldItem: View, newItem: View): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: View, newItem: View): Boolean {
            return oldItem.image == newItem.image
        }
    }

    inner class ViewPropertyViewHolder(private var binding: ViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: View) {
            binding.property = data
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                onItemClick?.invoke(data)
            }
        }
    }
}