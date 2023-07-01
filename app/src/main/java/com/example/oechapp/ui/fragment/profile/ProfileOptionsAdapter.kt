package com.example.oechapp.ui.fragment.profile

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.oechapp.databinding.ItemProfileOptionBinding

class ProfileOptionsAdapter(
    private val context: Context,
    private val items: List<ProfileOption>
) : RecyclerView.Adapter<ProfileOptionViewHolder>() {
    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileOptionViewHolder {
        val binding = ItemProfileOptionBinding.inflate(inflater, parent, false)
        return ProfileOptionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProfileOptionViewHolder, position: Int) {
        val option = items[position]
        holder.bind(option)
    }

    override fun getItemCount(): Int = items.size
}

class ProfileOptionViewHolder(val binding: ItemProfileOptionBinding) :
        RecyclerView.ViewHolder(binding.root) {
    fun bind(option: ProfileOption) {
        binding.icon.setImageResource(option.icon)
        binding.title.setText(option.title)
        binding.description.setText(option.description)

        binding.root.setOnClickListener {
            option.action.invoke()
        }
    }
}