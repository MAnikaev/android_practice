package com.itis.hw.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.itis.hw.R
import com.itis.hw.databinding.FragmentDetailMovieBinding
import com.itis.hw.di.ServiceLocator
import com.itis.hw.ui.MainActivity
import com.itis.hw.utils.ParamKeys
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailMovieFragment : Fragment(R.layout.fragment_detail_movie) {

    private val binding: FragmentDetailMovieBinding by viewBinding(FragmentDetailMovieBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as? MainActivity)?.hideBottomNavigationView()
        initButtons()
        initViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        (requireActivity() as? MainActivity)?.showBottomNavigationView()
    }

    @SuppressLint("SetTextI18n")
    private fun initButtons() {
        with(binding) {
            confirmBtn.apply {
                setOnClickListener {
                    val vote = ratingSb.progress
                    val movieId = arguments?.getInt(ParamKeys.MOVIE_ID_KEY)!!

                    lifecycleScope.launch(Dispatchers.IO) {

                        ServiceLocator.getDbInstance().movieDao().addVoteToMovie(movieId, vote)

                    }.invokeOnCompletion {

                        lifecycleScope.launch(Dispatchers.IO) {
                            val rating = ServiceLocator.getDbInstance().movieDao().getMovieRatingById(movieId)

                            withContext(Dispatchers.Main) {
                                ratingTv.text = "${ratingTv.text.split(':')[0]}: $rating"
                            }
                        }
                    }
                    isEnabled = false
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initViews() {
        val movieId = arguments?.getInt(ParamKeys.MOVIE_ID_KEY)
        lifecycleScope.launch(Dispatchers.IO) {
            val movie = ServiceLocator.getDbInstance().movieDao().getMovieById(movieId!!)!!
            withContext(Dispatchers.Main) {
                with(binding) {
                    confirmBtn.isEnabled = true
                    movieTitleTv.text = movie.title
                    movieDescTv.text = "${movieDescTv.text}${movie.description}"
                    ratingTv.text = "${ratingTv.text}${movie.rating}"
                    movieYearTv.text = "${movieYearTv.text}${movie.releaseYear}"
                    movieIv.load(movie.imageUrl) {
                        placeholder(R.drawable.error_img)
                        error(R.drawable.error_img)
                    }
                }
            }
        }
    }
}