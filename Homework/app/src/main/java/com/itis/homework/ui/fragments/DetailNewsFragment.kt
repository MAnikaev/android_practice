package com.itis.homework.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.itis.homework.R
import com.itis.homework.databinding.FragmentDetailNewsBinding

class DetailNewsFragment : Fragment(R.layout.fragment_detail_news) {

    private val binding: FragmentDetailNewsBinding by viewBinding(FragmentDetailNewsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            arguments.let {

            }
        }
    }

    companion object {
        const val DETAIL_NEWS_FRAGMENT_TAG = "DETAIL_NEWS_FRAGMENT_TAG"

        fun getInstance(): DetailNewsFragment =
            DetailNewsFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}