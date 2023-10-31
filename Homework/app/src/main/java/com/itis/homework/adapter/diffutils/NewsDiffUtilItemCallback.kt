package com.itis.homework.adapter.diffutils

import androidx.recyclerview.widget.DiffUtil
import com.itis.homework.model.NewsData

class NewsDiffUtilItemCallback : DiffUtil.ItemCallback<NewsData>(){
    override fun areItemsTheSame(oldItem: NewsData, newItem: NewsData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: NewsData, newItem: NewsData): Boolean {
        return oldItem.isFav == newItem.isFav
    }
}