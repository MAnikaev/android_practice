package com.itis.homework.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.itis.homework.R
import com.itis.homework.databinding.DialogCountFragmentBinding
import com.itis.homework.utils.NewsRepository

class CountDialogFragment : BottomSheetDialogFragment() {

    private val binding: DialogCountFragmentBinding by viewBinding(DialogCountFragmentBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_count_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calculateViewDialogHeight()
        initTextInputs()
        initButtons()
    }

    private fun calculateViewDialogHeight() {
        val displayMetrics = requireContext().resources.displayMetrics
        val dialogHeight = displayMetrics.heightPixels / 3

        val layoutParams =
            FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .apply {
                    height = dialogHeight
                }

        binding.root.layoutParams = layoutParams
    }

    private fun initTextInputs() {
        with(binding) {
            newsCountEt.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(input: CharSequence?, start: Int, before: Int, count: Int) {
                    if (input?.length == 2) {
                        newsCountEt.apply {
                            setText(input.subSequence(0, input.length - 1))
                            setSelection(1)
                        }
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                    val isValidRegex = Regex("^[0-5]$")
                    addNewsBtn.isEnabled = isValidRegex.matches(newsCountEt.text.toString())
                }
            })
        }
    }

    private fun initButtons() {
        with(binding) {
            addNewsBtn.setOnClickListener {
                val count = newsCountEt.text.toString().toInt()
                val newsFragment = parentFragmentManager.findFragmentByTag(NewsFragment.NEWS_FRAGMENT_TAG) as? NewsFragment
                val newsAdapter = newsFragment?.adapter
                newsAdapter?.setNewNews(NewsRepository.addNews(count, newsAdapter.news))
                this@CountDialogFragment.dismiss()
            }
        }
    }

    companion object {
        const val COUNT_DIALOG_FRAGMENT_TAG = "COUNT_DIALOG_FRAGMENT_TAG"

        fun getInstance() = CountDialogFragment()
    }
}