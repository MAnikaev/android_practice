package com.itis.homework.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.itis.homework.R
import com.itis.homework.databinding.FragmentDetailNewsBinding
import com.itis.homework.utils.ParamsKey

class DetailNewsFragment : Fragment(R.layout.fragment_detail_news) {

    private val binding: FragmentDetailNewsBinding by viewBinding(FragmentDetailNewsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            arguments.let {
                newsDescriptionTv.text = it?.getString(ParamsKey.NEWS_DESCRIPTION_KEY)
                newsNameTv.text = it?.getString(ParamsKey.NEWS_TITLE_KEY)
                val imageUrl = it?.getString(ParamsKey.NEWS_IMAGE_URL_KEY)
                newsImage.load(imageUrl) {
                    error(R.drawable.error_img)
                    placeholder(R.drawable.error_img)
                }
            }
        }
    }

    companion object {
        const val DETAIL_NEWS_FRAGMENT_TAG = "DETAIL_NEWS_FRAGMENT_TAG"

        fun getInstance(title: String, imageUrl: String, description: String): DetailNewsFragment =
            DetailNewsFragment().apply {
                arguments = Bundle().apply {
                    putString(ParamsKey.NEWS_TITLE_KEY, title)
                    putString(ParamsKey.NEWS_DESCRIPTION_KEY, description)
                    putString(ParamsKey.NEWS_IMAGE_URL_KEY, imageUrl)
                }
            }
    }
}