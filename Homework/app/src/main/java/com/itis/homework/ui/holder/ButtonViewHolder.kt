package com.itis.homework.ui.holder

import androidx.recyclerview.widget.RecyclerView
import com.itis.homework.R
import com.itis.homework.databinding.ItemButtonBinding

class ButtonViewHolder(
    private val binding: ItemButtonBinding,
    private val action: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.addNewsBtn.setOnClickListener {
            action.invoke()
        }
    }
}