package com.itis.homework.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.itis.homework.R
import com.itis.homework.adapter.NewsAdapter
import com.itis.homework.adapter.diffutils.NewsDiffUtilItemCallback
import com.itis.homework.databinding.FragmentNewsBinding
import com.itis.homework.model.BaseActivity
import com.itis.homework.utils.ActionType
import com.itis.homework.utils.NewsRepository
import com.itis.homework.utils.ParamsKey
import com.itis.homework.utils.ViewHolderType

class NewsFragment : Fragment(R.layout.fragment_news) {

    private val binding: FragmentNewsBinding by viewBinding(FragmentNewsBinding::bind)

    var adapter: NewsAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val newsCount = arguments?.getInt(ParamsKey.NEWS_COUNT_KEY)
        initRecyclerView(newsCount)
    }
    private fun initRecyclerView(newsCount: Int?) {
        val dates = mutableListOf<String>()
        dates.addAll(resources.getStringArray(R.array.dates))

        val news = NewsRepository.getNews(newsCount)
        val adapter = NewsAdapter(
            diffUtil = NewsDiffUtilItemCallback(),
            news = news,
            buttonAction = {
                CountDialogFragment().show(parentFragmentManager, CountDialogFragment.COUNT_DIALOG_FRAGMENT_TAG)
            },
            imageAction = {position ->
                val newsData = news[position]
                (requireActivity() as? BaseActivity)?.goToScreen(
                    action = ActionType.Replace,
                    destination = DetailNewsFragment.getInstance(title = newsData.title, imageUrl = newsData.imageUrl, description = newsData.description),
                    tag = DetailNewsFragment.DETAIL_NEWS_FRAGMENT_TAG
                )
            },
            dates = dates
        )
        with(binding) {
            newsRv.adapter = adapter
            this@NewsFragment.adapter = adapter
            if (newsCount != null) {
                if(newsCount > 12) {
                    val layoutManager = GridLayoutManager(requireContext(), 2)
                    layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                        override fun getSpanSize(position: Int): Int =
                            when (adapter.getItemViewType(position)) {
                                ViewHolderType.Button.ordinal -> 2
                                ViewHolderType.News.ordinal -> 1
                                ViewHolderType.Date.ordinal -> 2
                                else -> 1
                            }


                    }
                    newsRv.layoutManager = layoutManager
                } else {
                    val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                    newsRv.layoutManager = layoutManager
                }
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