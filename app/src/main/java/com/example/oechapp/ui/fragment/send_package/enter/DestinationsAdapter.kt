package com.example.oechapp.ui.fragment.send_package.enter

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.oechapp.databinding.ItemAddressDetailsInputsBinding
import com.example.oechapp.databinding.ItemDestinationDetailsInputsBinding

class DestinationsAdapter(private val context: Context) :
        RecyclerView.Adapter<ItemDestinationViewHolder>() {
    private val inflater = LayoutInflater.from(context)

    private val tempStore: MutableList<DestinationContent> = arrayListOf(
        DestinationContent()
    )

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<DestinationContent>) {
        tempStore.clear()

        if (items.isEmpty()) {
            addEmptyItem()
        } else {
            tempStore.addAll(items)
            notifyDataSetChanged()
        }
    }

    fun addEmptyItem() {
        tempStore.add(DestinationContent())
        notifyItemInserted(itemCount - 1)
    }

    fun collectData(): List<DestinationContent> {
        return tempStore
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemDestinationViewHolder {
        val binding = ItemDestinationDetailsInputsBinding.inflate(inflater, parent, false)
        return ItemDestinationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemDestinationViewHolder, position: Int) {
        val content = tempStore[position]
        holder.bind(content)
    }

    override fun getItemCount(): Int = tempStore.size
}

class ItemDestinationViewHolder(val binding: ItemDestinationDetailsInputsBinding) :
        RecyclerView.ViewHolder(binding.root) {
    private val inputs = ItemAddressDetailsInputsBinding.bind(binding.root)

    private var addressWatcher: MyEditTextListener? = null
    private var countryWatcher: MyEditTextListener? = null
    private var phoneWatcher: MyEditTextListener? = null
    private var othersWatcher: MyEditTextListener? = null

    fun bind(content: DestinationContent) {
        detachWatchers()

        inputs.address.setText(content.address)
        inputs.country.setText(content.country)
        inputs.phone.setText(content.phoneNumber)
        inputs.others.setText(content.others)

        attachWatchers(content)
    }

    private fun detachWatchers() {
        addressWatcher?.let { inputs.address.removeTextChangedListener(it) }
        countryWatcher?.let { inputs.country.removeTextChangedListener(it) }
        addressWatcher?.let { inputs.phone.removeTextChangedListener(it) }
        othersWatcher?.let { inputs.others.removeTextChangedListener(it) }
    }

    private fun attachWatchers(content: DestinationContent) {
        addressWatcher = MyEditTextListener {
            content.address = it
        }

        countryWatcher = MyEditTextListener {
            content.country = it
        }

        phoneWatcher = MyEditTextListener {
            content.phoneNumber = it
        }

        othersWatcher = MyEditTextListener {
            content.others = it
        }

        inputs.address.addTextChangedListener(addressWatcher)
        inputs.country.addTextChangedListener(countryWatcher)
        inputs.phone.addTextChangedListener(phoneWatcher)
        inputs.others.addTextChangedListener(othersWatcher)
    }
}

class MyEditTextListener(
    private val onTextChanged: (str: String) -> Unit
) : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    override fun afterTextChanged(s: Editable?) {
        onTextChanged.invoke(s?.toString() ?: "")
    }
}