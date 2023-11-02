package com.itis.homework.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.itis.homework.databinding.FragmentNewsBinding
import com.itis.homework.databinding.ItemButtonBinding
import com.itis.homework.databinding.ItemDateBinding
import com.itis.homework.databinding.ItemNewsBinding
import com.itis.homework.model.NewsData
import com.itis.homework.ui.fragments.CountDialogFragment
import com.itis.homework.ui.holder.ButtonViewHolder
import com.itis.homework.ui.holder.DateViewHolder
import com.itis.homework.ui.holder.NewsViewHolder
import com.itis.homework.utils.ViewHolderType
import java.util.Date

class NewsAdapter(
    diffUtil: DiffUtil.ItemCallback<NewsData>,
    var news: MutableList<NewsData>,
    private val buttonAction: () -> Unit,
    private val imageAction: (Int) -> Unit,
    private val dates: MutableList<String>
) : ListAdapter<NewsData, RecyclerView.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType) {
            ViewHolderType.News.ordinal -> {
                return NewsViewHolder(
                    binding = ItemNewsBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false),
                    onImageClicked = imageAction,
                    onFavClicked = { pos, listPos ->
                        val newFavFlag = !(news[listPos].isFav)
                    news[listPos].isFav = newFavFlag
                    notifyItemChanged(pos, newFavFlag)
                })
            }
            ViewHolderType.Date.ordinal -> {
                return DateViewHolder(
                    binding = ItemDateBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false)
                )
            }
            else -> {
                return ButtonViewHolder(
                    binding = ItemButtonBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false),
                    action = buttonAction
                )
            }
        }
    }

    override fun getItemCount(): Int = news.size + 1 + news.size / 8

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is NewsViewHolder -> {
                holder.bindItem(news[position - 1 - (position + 1) / 8])
            }
            is DateViewHolder -> {
                holder.bindItem(dates[(position - 1) / 8 - 1])
            }
            is ButtonViewHolder -> Unit
            else -> Unit
        }
    }

    override fun getItemViewType(position: Int): Int =
        if(position == 0) {
            ViewHolderType.Button.ordinal
        } else if (position % 9 == 0) {
            ViewHolderType.Date.ordinal
        } else {
            ViewHolderType.News.ordinal
        }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewNews(newNews: MutableList<NewsData>) {
        news = newNews
        notifyDataSetChanged()
    }
}