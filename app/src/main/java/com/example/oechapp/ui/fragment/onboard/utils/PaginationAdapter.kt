package com.example.oechapp.ui.fragment.onboard.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.oechapp.databinding.ItemDotBinding

class PaginationAdapter(
    context: Context,
    private val pageCount: Int,
    private val activePage: Int
) : RecyclerView.Adapter<PaginationViewHolder>() {
    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaginationViewHolder {
        val binding = ItemDotBinding.inflate(inflater, parent, false)
        return PaginationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PaginationViewHolder, position: Int) {
        holder.bind(position == activePage)
    }

    override fun getItemCount(): Int = pageCount
}

class PaginationViewHolder(private val binding: ItemDotBinding) :
        RecyclerView.ViewHolder(binding.root) {
    fun bind(isSelected: Boolean) {
        binding.dot.isSelected = isSelected
    }
}