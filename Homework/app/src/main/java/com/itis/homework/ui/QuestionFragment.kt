package com.itis.homework.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import by.kirich1409.viewbindingdelegate.viewBinding
import com.itis.homework.adapter.AnswerAdapter
import com.itis.homework.utils.AnswersGenerator
import com.itis.homework.R
import com.itis.homework.databinding.FragmentQuestionBinding
import com.itis.homework.databinding.FragmentQuestionnaireBinding
import com.itis.homework.model.AnswerData
import com.itis.homework.utils.ParamKeys
import java.lang.Exception
import kotlin.random.Random

class QuestionFragment : Fragment(R.layout.fragment_question) {

    private val binding: FragmentQuestionBinding by viewBinding(FragmentQuestionBinding::bind)
    private var adapter: AnswerAdapter? = null
    var items: MutableList<AnswerData>? = null
    var checkArray: Array<Boolean>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initRecyclerView()
    }
    private fun initRecyclerView() {
        adapter = AnswerAdapter(
            items = items!!,
            onItemChecked = { checkArray, position, isChecked ->
                adapter?.items?.let {
                    val numberOfQuestion = arguments?.getString(ParamKeys.QUESTION_NUMBER_KEY).toString().toInt()
                    it[position].isChecked = isChecked
                    checkArray[numberOfQuestion - 1] = true
                }
            },
            checkArray!!
        )
        binding.answersRv.adapter = adapter
    }

    private fun initViews() {
        with(binding) {
            questionTv.text = arguments?.getString(ParamKeys.QUESTION_TEXT_KEY).toString()
            questionNumberTv.text = arguments?.getString(ParamKeys.QUESTION_NUMBER_KEY).toString()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
    }

    companion object {
        fun getInstance(question: String, number: String) =
            QuestionFragment().apply {
                arguments = Bundle().apply {
                    putString(ParamKeys.QUESTION_TEXT_KEY, question)
                    putString(ParamKeys.QUESTION_NUMBER_KEY, number)
                }
            }
    }
}