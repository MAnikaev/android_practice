package com.itis.homework.ui.holder

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.itis.homework.R
import com.itis.homework.databinding.ItemNewsBinding
import com.itis.homework.model.NewsData

class NewsViewHolder(
    private val binding: ItemNewsBinding,
    private val onFavClicked: (position: Int) -> Unit
//    private val onRootClicked: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {


    init {
        with(binding) {
            favouriteBtn.setOnClickListener {
                onFavClicked.invoke(adapterPosition)
            }
            root.setOnClickListener {
               // onRootClicked.invoke()
            }
        }
    }

    fun bindItem(newsData: NewsData) {
        with(binding) {
            newsNameTv.text = newsData.title
            newsImage.load(newsData.imageUrl) {
                //placeholder(R.drawable.error_img)
                error(R.drawable.error_img)
            }
            val favImage = if (newsData.isFav) R.drawable.baseline_favorite_24_red else R.drawable.baseline_favorite_24_black
            favouriteBtn.setImageResource(favImage)
        }
    }
}