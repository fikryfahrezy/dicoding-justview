package com.dicoding.justview.favorite.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.justview.core.databinding.ViewItemBinding
import com.dicoding.justview.core.domain.model.View

enum class ClickType { SHORT, LONG }

class ClickListener(val clickListener: (data: View, clickType: ClickType) -> Unit) {
    fun onClick(data: View, type: ClickType) = clickListener(data, type)
}

class FavoriteAdapter(private val clickListener: ClickListener) :
    RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    private val data = ArrayList<View>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = ViewItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], clickListener)
    }

    override fun getItemCount(): Int = data.size

    fun updateData(newViewEntities: List<View>) {
        data.clear()
        data.addAll(newViewEntities)
        notifyDataSetChanged()
    }

    fun getSwipedData(swipedPosition: Int): View = data[swipedPosition]

    class ViewHolder(private val binding: ViewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: View, clickListener: ClickListener) = with(binding) {
            property = data
            itemContainer.setOnClickListener { clickListener.onClick(data, ClickType.SHORT) }
            itemContainer.setOnLongClickListener {
                clickListener.onClick(data, ClickType.LONG)
                return@setOnLongClickListener true
            }
        }
    }
}