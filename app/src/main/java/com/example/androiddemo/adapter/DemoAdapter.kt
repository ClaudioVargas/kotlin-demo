package com.example.androiddemo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androiddemo.databinding.ItemDemoBinding
import com.example.androiddemo.model.DemoItem

/**
 * Adapter del RecyclerView principal. Ejemplo básico de RecyclerView.Adapter.
 */
class DemoAdapter(
    private val items: List<DemoItem>,
    private val onClick: (DemoItem) -> Unit
) : RecyclerView.Adapter<DemoAdapter.DemoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DemoViewHolder {
        val binding = ItemDemoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DemoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DemoViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class DemoViewHolder(
        private val binding: ItemDemoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DemoItem) {
            val context = binding.root.context
            binding.imageViewIcon.setImageResource(item.icon)
            binding.textViewTitle.text = context.getString(item.title)
            binding.textViewDescription.text = context.getString(item.description)
            binding.root.setOnClickListener { onClick(item) }
        }
    }
}