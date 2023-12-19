package com.itis.hw.ui.fragments

import android.app.AlertDialog
import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.itis.hw.R
import com.itis.hw.data.entity.MovieEntity
import com.itis.hw.data.entity.UserMovieCrossRef
import com.itis.hw.databinding.FragmentMainBinding
import com.itis.hw.di.ServiceLocator
import com.itis.hw.models.Session
import com.itis.hw.ui.adapter.MovieAdapter
import com.itis.hw.utils.ParamKeys
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainFragment : Fragment(R.layout.fragment_main) {
    private val binding: FragmentMainBinding by viewBinding(FragmentMainBinding::bind)
    private var favoriteMoviesAdapter: MovieAdapter? = null
    private var allMoviesAdapter: MovieAdapter? = null

    private var allMovies: MutableList<MovieEntity> = mutableListOf()
    private var favoriteMovies: MutableList<MovieEntity> = mutableListOf()


    private fun initMovieLists() {
        lifecycleScope.launch(Dispatchers.IO) {

            val userEmail = ServiceLocator.getGson().fromJson(
                ServiceLocator.getPrefs().getString(ParamKeys.SESSION_KEY, null),
                Session::class.java
            ).userEmail

            allMovies = ServiceLocator.getDbInstance().movieDao().getAllMovies().sortedByDescending { movie -> movie.releaseYear }.toMutableList()
            favoriteMovies = ServiceLocator.getDbInstance().movieUserDao().getMoviesForUser(userEmail).sortedByDescending { m -> m.releaseYear }.toMutableList()

        }.invokeOnCompletion {
            lifecycleScope.launch(Dispatchers.Main) {
                initAdapters()
                initRecyclerViews()
                if(favoriteMovies.size == 0) {
                    binding.favMoviesTv.visibility = View.GONE
                }

                if(allMovies.size == 0) {
                    AlertDialog.Builder(requireContext())
                        .setTitle(getString(R.string.no_movies_alert_text))
                        .setPositiveButton(R.string.ok) { dialog, _ ->
                            dialog.dismiss()
                        }
                        .show()
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMovieLists()
    }

    private fun initAdapters() {
        allMoviesAdapter = MovieAdapter(
            movies = allMovies,
            onFavClicked = {movie, icon ->
                if(favoriteMovies.size == 0) {
                    binding.favMoviesTv.visibility = View.VISIBLE
                }
                onAddRemoveClicked(movie, icon)
                if(favoriteMovies.size == 0) {
                    binding.favMoviesTv.visibility = View.GONE
                }
            },
            onImageClicked = { id ->
                findNavController().navigate(R.id.action_mainFragment_to_detailMovieFragmentDialog, bundleOf(ParamKeys.MOVIE_ID_KEY to id))
            },
            onItemLoaded = {id, icon ->
                initFavIcon(id, icon)
            },
            onLongPress = { id ->
                deleteMovie(id)
            }
        )

        favoriteMoviesAdapter = MovieAdapter(
            movies = favoriteMovies,
            onFavClicked = { movie, _ ->
                removeMovieFromFavorites(movie)
                val index = allMovies.indexOf(movie)
                allMoviesAdapter!!.notifyItemChanged(index, listOf(true))
                if(favoriteMovies.size == 0) {
                    binding.favMoviesTv.visibility = View.GONE
                }
            },
            onImageClicked = {id ->
                findNavController().navigate(R.id.action_mainFragment_to_detailMovieFragmentDialog, bundleOf(ParamKeys.MOVIE_ID_KEY to id))
            },
            onItemLoaded = { _, icon ->
                icon.setImageResource(R.drawable.baseline_favorite_24_red)
            },
            onLongPress = {id ->
                deleteMovie(id)
            }
        )
    }

    private fun initRecyclerViews() {
        binding.allMoviesRv.adapter = allMoviesAdapter
        binding.favoritesRv.adapter = favoriteMoviesAdapter
    }

    private fun deleteMovie(id: Int) {
        lifecycleScope.launch(Dispatchers.IO) {
            val userEmail = ServiceLocator.getGson().fromJson(
                ServiceLocator.getPrefs().getString(ParamKeys.SESSION_KEY, null),
                Session::class.java
            ).userEmail

            ServiceLocator.getDbInstance().movieUserDao().deleteUserMovieJoin(
                UserMovieCrossRef(
                    userEmail = userEmail,
                    movieId = id
            ))

            ServiceLocator.getDbInstance().movieDao().deleteMovie(id)

            withContext(Dispatchers.Main) {
                val allIndex = allMovies.map { movie -> movie.id }.indexOf(id)
                allMoviesAdapter?.notifyItemRemoved(allIndex)
                allMovies.removeAt(allIndex)

                if(favoriteMovies.map{m -> m.id}.contains(id)) {
                    val favIndex = favoriteMovies.map { m -> m.id }.indexOf(id)
                    favoriteMoviesAdapter?.notifyItemRemoved(favIndex)
                    favoriteMovies.removeAt(favIndex)
                }
            }
        }
    }

    private fun initFavIcon(movieId: Int, image: ImageView) {
        image.setImageResource(
            if(favoriteMovies.map{m -> m.id}.contains(movieId))
                R.drawable.baseline_favorite_24_red
            else
                R.drawable.baseline_favorite_24
        )
    }

    private fun onAddRemoveClicked(movie: MovieEntity, icon: ImageView) {
        if (favoriteMovies.contains(movie)) {
            removeMovieFromFavorites(movie)
            icon.setImageResource(R.drawable.baseline_favorite_24)
        } else {
            addMovieToFavorites(movie)
            icon.setImageResource(R.drawable.baseline_favorite_24_red)
        }
    }

    private fun addMovieToFavorites(movie: MovieEntity) {
        favoriteMovies.add(movie)
        favoriteMoviesAdapter?.notifyItemInserted(favoriteMovies.indexOf(movie))

        lifecycleScope.launch(Dispatchers.IO) {
            val userEmail = ServiceLocator.getGson().fromJson(
                ServiceLocator.getPrefs().getString(ParamKeys.SESSION_KEY, null),
                Session::class.java
            ).userEmail

            ServiceLocator.getDbInstance().movieUserDao().insertUserMovieJoin(
                UserMovieCrossRef(
                    movieId = movie.id,
                    userEmail = userEmail
                )
            )
        }
    }

    private fun removeMovieFromFavorites(movie: MovieEntity) {
        favoriteMoviesAdapter?.notifyItemRemoved(favoriteMovies.indexOf(movie))
        favoriteMovies.remove(movie)

        lifecycleScope.launch(Dispatchers.IO) {
            val userEmail = ServiceLocator.getGson().fromJson(
                ServiceLocator.getPrefs().getString(ParamKeys.SESSION_KEY, null),
                Session::class.java
            ).userEmail

            ServiceLocator.getDbInstance().movieUserDao().deleteUserMovieJoin(
                UserMovieCrossRef(
                    movieId = movie.id,
                    userEmail = userEmail
                )
            )
        }
    }
}