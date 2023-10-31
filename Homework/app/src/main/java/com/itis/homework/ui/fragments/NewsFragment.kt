package com.itis.homework.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.itis.homework.R
import com.itis.homework.adapter.NewsAdapter
import com.itis.homework.adapter.diffutils.NewsDiffUtilItemCallback
import com.itis.homework.databinding.FragmentNewsBinding
import com.itis.homework.model.BaseActivity
import com.itis.homework.utils.ActionType
import com.itis.homework.utils.NewsRepository
import com.itis.homework.utils.ParamsKey

class NewsFragment : Fragment(R.layout.fragment_news) {

    private val binding: FragmentNewsBinding by viewBinding(FragmentNewsBinding::bind)

    var adapter: NewsAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val newsCount = arguments?.getInt(ParamsKey.NEWS_COUNT_KEY)
        initRecyclerView(newsCount)
        initButtons()
    }

    private fun initRecyclerView(newsCount: Int?) {
        val adapter = NewsAdapter(
            diffUtil = NewsDiffUtilItemCallback(),
            parentBinding = binding,
            context = requireContext(),
            items = NewsRepository.getNews(newsCount)
        )
        with(binding) {
            newsRv.adapter = adapter
            this@NewsFragment.adapter = adapter
            if (newsCount != null) {
                newsRv.layoutManager =
                    if(newsCount <= 12)
                        LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                    else
                        GridLayoutManager(requireContext(), 2)
            }
        }
    }
    private fun initButtons() {
        with(binding) {
            addNewsBtn.setOnClickListener {
                CountDialogFragment().show(parentFragmentManager, CountDialogFragment.COUNT_DIALOG_FRAGMENT_TAG)
            }
        }
    }
    companion object {
        const val NEWS_FRAGMENT_TAG = "NEWS_FRAGMENT_TAG"

        fun getInstance(newsCount: Int): NewsFragment =
            NewsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ParamsKey.NEWS_COUNT_KEY, newsCount)
                }
            }
    }
}