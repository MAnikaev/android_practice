package com.itis.homework.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.itis.homework.databinding.FragmentNewsBinding
import com.itis.homework.databinding.ItemNewsBinding
import com.itis.homework.model.NewsData
import com.itis.homework.ui.holder.NewsViewHolder

class NewsAdapter(
    diffUtil: DiffUtil.ItemCallback<NewsData>,
    private val parentBinding: FragmentNewsBinding,
    private val context: Context,
    var items: MutableList<NewsData>,
) : ListAdapter<NewsData, NewsViewHolder>(diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder =
        NewsViewHolder(
            binding = ItemNewsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false)
        ) { pos ->
            items[pos].isFav = !items[pos].isFav
            notifyItemChanged(pos, items[pos].isFav)
        }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bindItem(items[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNews(newNews: MutableList<NewsData>) {
        items = newNews
        parentBinding.newsRv.layoutManager =
            if(items.size <= 12)
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            else
                GridLayoutManager(context, 2)

        notifyDataSetChanged()
    }
}