package com.example.oechapp.ui.fragment.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.oechapp.databinding.ItemMainActionBinding
import com.example.oechapp.ui.utils.themeRes
import com.google.android.material.R as MaterialRes

class MainActionsAdapter(
    private val context: Context,
    private val actions: List<MainAction>
) : RecyclerView.Adapter<MainActionViewHolder>() {
    private val inflater = LayoutInflater.from(context)

    private var selectedItem: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainActionViewHolder {
        val binding = ItemMainActionBinding.inflate(inflater, parent, false)
        return MainActionViewHolder(context, binding)
    }

    override fun getItemCount(): Int = actions.size

    override fun onBindViewHolder(holder: MainActionViewHolder, position: Int) {
        val action = actions[position]

        holder.bind(action)
        holder.updateCardColors(selectedItem == position)

        holder.binding.root.setOnClickListener {
            selectedItem = holder.adapterPosition
            notifyDataSetChanged()

            action.action.invoke()
        }
    }
}

class MainActionViewHolder(val context: Context, val binding: ItemMainActionBinding) :
        RecyclerView.ViewHolder(binding.root) {
    fun bind(action: MainAction) {
        binding.icon.setImageResource(action.icon)
        binding.title.setText(action.title)
        binding.descriptino.setText(action.description)
    }

    fun updateCardColors(isSelected: Boolean) {
        if (isSelected) {
            binding.root.setCardBackgroundColor(context.themeRes(MaterialRes.attr.colorPrimary))
            binding.icon.setColorFilter(context.themeRes(MaterialRes.attr.colorOnPrimary))
            binding.title.setTextColor(context.themeRes(MaterialRes.attr.colorOnPrimary))
            binding.descriptino.setTextColor(context.themeRes(MaterialRes.attr.colorOnPrimary))
        } else {
            binding.root.setCardBackgroundColor(context.themeRes(MaterialRes.attr.colorSurfaceVariant))
            binding.icon.setColorFilter(context.themeRes(MaterialRes.attr.colorPrimary))
            binding.title.setTextColor(context.themeRes(MaterialRes.attr.colorPrimary))
            binding.descriptino.setTextColor(context.themeRes(MaterialRes.attr.colorOnSurfaceVariant))
        }
    }
}
