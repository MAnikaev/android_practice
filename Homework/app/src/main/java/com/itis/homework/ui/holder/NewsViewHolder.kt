package com.itis.homework.ui.holder

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.itis.homework.R
import com.itis.homework.databinding.ItemNewsBinding
import com.itis.homework.model.NewsData
import kotlin.time.measureTimedValue

class NewsViewHolder(
    private val binding: ItemNewsBinding,
    private val onFavClicked: (position: Int, listPosition: Int) -> Unit,
    private val onImageClicked: (position: Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        with(binding) {
            favouriteBtn.setOnClickListener {
                if (adapterPosition == 1) {
                    onFavClicked.invoke(1, 0)
                } else if (adapterPosition == 2) {
                    onFavClicked.invoke(2, 1)
                } else {
                    onFavClicked.invoke(adapterPosition, adapterPosition - 1 - adapterPosition / 9)
                }
            }
            newsImage.setOnClickListener {
                onImageClicked.invoke(adapterPosition - 1 - adapterPosition / 9)
            }
        }
    }

    fun bindItem(newsData: NewsData) {
        with(binding) {
            newsNameTv.text = newsData.title
            newsImage.load(newsData.imageUrl) {
                placeholder(R.drawable.error_img)
                error(R.drawable.error_img)
            }
            val favImage = if (newsData.isFav) R.drawable.baseline_favorite_24_red else R.drawable.baseline_favorite_24_black
            favouriteBtn.setImageResource(favImage)
        }
    }
}