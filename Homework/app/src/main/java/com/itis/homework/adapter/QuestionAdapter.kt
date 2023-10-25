package com.itis.homework.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.itis.homework.model.AnswerData
import com.itis.homework.ui.QuestionFragment
import com.itis.homework.utils.AnswersGenerator
import kotlin.random.Random

class QuestionAdapter(private val questions: Array<String>, private val questionCount: Int, manager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(manager, lifecycle) {

    val checkArray = Array<Boolean>(questionCount) { _ -> true }

    private val fragmentsData = Array<MutableList<AnswerData>>(questionCount) {_ -> AnswersGenerator.getAnswers()}

    override fun getItemCount(): Int = questionCount
    override fun createFragment(position: Int): Fragment =
        QuestionFragment.getInstance(question = questions[position], (position + 1).toString()).apply {
            items = fragmentsData[position]
        }
}

