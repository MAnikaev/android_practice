package com.itis.homework.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import com.itis.homework.R
import com.itis.homework.adapter.QuestionAdapter
import com.itis.homework.databinding.FragmentQuestionnaireBinding
import com.itis.homework.utils.ParamKeys


class QuestionnaireFragment : Fragment(R.layout.fragment_questionnaire) {

    private val binding: FragmentQuestionnaireBinding by viewBinding(FragmentQuestionnaireBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initButtons()

        val adapter = QuestionAdapter(
            questions = resources.getStringArray(R.array.questions),
            questionCount = arguments?.getString(ParamKeys.QUESTION_COUNT_KEY).toString().toInt(),
            manager = parentFragmentManager,
            lifecycle = lifecycle
        )

        binding.mainVp.adapter = adapter
        val onPageChangeListener = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                val isLastFragment = position == adapter.itemCount - 1

                binding.finishBtn.isVisible = isLastFragment
               // binding.finishBtn.isEnabled =
            }
        }
        binding.mainVp.registerOnPageChangeCallback(onPageChangeListener)
    }

    private fun initButtons() {
        binding.finishBtn.setOnClickListener {
            Snackbar.make(binding.root, "Вы прошли опрос", 10000).show()
        }
    }

    companion object {
        fun getInstance(questionCount: String) =
            QuestionnaireFragment().apply {
                arguments = Bundle().apply {
                    putString(ParamKeys.QUESTION_COUNT_KEY, questionCount)
                }
            }
    }
}