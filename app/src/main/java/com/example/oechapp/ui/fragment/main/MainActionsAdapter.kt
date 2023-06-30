package com.example.oechapp.ui.fragment.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.oechapp.databinding.ItemMainActionBinding
import com.example.oechapp.ui.utils.themeRes
import com.google.android.material.R as MaterialRes

class MainActionsAdapter(
    private val context: Context,
    private val actions: List<MainAction>
) : BaseAdapter() {
    private val inflater = LayoutInflater.from(context)

    private var selectedItem: Int? = null

    override fun getCount(): Int = actions.size

    override fun getItem(position: Int): MainAction = actions[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding =
            if (convertView == null) ItemMainActionBinding.inflate(inflater, parent, false)
            else ItemMainActionBinding.bind(convertView)

        val action = getItem(position)

        binding.icon.setImageResource(action.icon)
        binding.title.setText(action.title)
        binding.descriptino.setText(action.description)

        updateCardColors(selectedItem == position, binding)

        binding.root.setOnClickListener {
            selectedItem = position
            notifyDataSetChanged()
        }

        return binding.root
    }

    private fun updateCardColors(isSelected: Boolean, binding: ItemMainActionBinding) {
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
