package com.itis.hw.ui.items

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import coil.load
import com.itis.hw.R
import com.itis.hw.data.entity.MovieEntity
import com.itis.hw.databinding.ItemMovieBinding
import com.itis.hw.ui.adapter.MovieAdapter

class MovieItem(
    private val binding: ItemMovieBinding,
    private val onFavBtnClicked: (MovieEntity, ImageView) -> Unit,
    private val onImageClicked: (Int) -> Unit,
    private val onLoad: (Int, ImageView) -> Unit,
    private val onLongPress: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {


    fun bindItem(movie: MovieEntity) {
        with(binding) {
            onLoad(movie.id, favBtn)

            movieIv.load(movie.imageUrl) {
                placeholder(R.drawable.error_img)
                error(R.drawable.error_img)
            }

            favBtn.setOnClickListener {
                onFavBtnClicked(movie, binding.favBtn)
            }

            movieIv.setOnClickListener {
                onImageClicked(movie.id)
            }

            movieIv.setOnLongClickListener {
                onLongPress(movie.id)
                true
            }

            titleTv.text = movie.title
        }
    }

    fun changeFavIcon() {
        binding.favBtn.setImageResource(R.drawable.baseline_favorite_24)
    }
}