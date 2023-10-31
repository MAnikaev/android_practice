package com.itis.homework.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.itis.homework.R
import com.itis.homework.databinding.FragmentStartBinding
import com.itis.homework.model.BaseActivity
import com.itis.homework.utils.ActionType

class StartFragment : Fragment(R.layout.fragment_start) {

    private val binding: FragmentStartBinding by viewBinding(FragmentStartBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initTextInputs()
        initButtons()
    }

    private fun initButtons() {
        with(binding) {
            toNewsBtn.setOnClickListener {
                (requireActivity() as? BaseActivity)?.goToScreen(
                    action = ActionType.Replace,
                    destination = NewsFragment.getInstance(newsCountEt.text.toString().toInt()),
                    tag = NewsFragment.NEWS_FRAGMENT_TAG
                )
            }
        }
    }

    private fun initTextInputs() {
        with(binding) {
            newsCountEt.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(input: CharSequence?, start: Int, before: Int, count: Int) {
                    if (input?.length == 3) {
                        newsCountEt.apply {
                            setText(input.subSequence(0, input.length - 1))
                            setSelection(2)
                        }
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                    val isValidRegex = Regex("^(?:[0-9]|[1-3][0-9]|4[0-5])$")
                    toNewsBtn.isEnabled =  isValidRegex.matches(newsCountEt.text.toString())
                }

            })
        }
    }



    companion object {
        const val START_FRAGMENT_TAG = "START_FRAGMENT_TAG"

        fun getInstance(): StartFragment = StartFragment()
    }
}