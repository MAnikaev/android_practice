package com.itis.homework.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.itis.homework.R
import com.itis.homework.ui.QuestionFragment

class QuestionAdapter(private val questions: Array<String>, private val questionCount: Int, manager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(manager, lifecycle) {

    override fun getItemCount(): Int = questionCount

    override fun createFragment(position: Int): Fragment =
        QuestionFragment.getInstance(question = questions[position], (position + 1).toString())

}