package com.itis.homework.utils

import com.itis.homework.model.AnswerData

object AnswersGenerator {
    fun getAnswers(count: Int) : MutableList<AnswerData> {
        val list = mutableListOf<AnswerData>(
            AnswerData("1", false),
            AnswerData("2", false),
            AnswerData("3", false),
            AnswerData("4", false),
            AnswerData("5", false),
            AnswerData("6", false),
            AnswerData("7", false),
            AnswerData("8", false),
            AnswerData("9", false),
            AnswerData("10", false),
        )
        return list.subList(0, count - 1)
    }
}