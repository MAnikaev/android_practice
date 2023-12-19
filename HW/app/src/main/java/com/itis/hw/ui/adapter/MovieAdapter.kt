package com.itis.hw.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.itis.hw.data.entity.MovieEntity
import com.itis.hw.databinding.ItemMovieBinding
import com.itis.hw.ui.items.MovieItem

class MovieAdapter(
    private val movies: MutableList<MovieEntity>,
    private val favMovies: MutableList<MovieEntity>? = null,
    private val onFavClicked: (MovieEntity, ImageView) -> Unit = {_, _ ->},
    private val onImageClicked: (Int) -> Unit = {},
    private val onItemLoaded: (Int, ImageView) -> Unit = {_, _ ->},
    private val onLongPress: (Int) -> Unit = {id ->}
) : RecyclerView.Adapter<MovieItem>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItem =
        MovieItem(
            binding = ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context), parent, false),
            onFavBtnClicked = onFavClicked,
            onImageClicked = onImageClicked,
            onLoad = onItemLoaded,
            onLongPress = onLongPress
        )

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieItem, position: Int) {
        holder.bindItem(movies[position])
    }

    override fun onBindViewHolder(holder: MovieItem, position: Int, payloads: MutableList<Any>) {
        if(payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            holder.changeFavIcon()
        }
    }

}