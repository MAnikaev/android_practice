package com.itis.homework.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import com.itis.homework.model.BaseActivity
import com.itis.homework.R
import com.itis.homework.databinding.FragmentStartBinding
import com.itis.homework.utils.ActionType

class StartFragment : Fragment(R.layout.fragment_start) {
    private val binding: FragmentStartBinding by viewBinding(FragmentStartBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initButtons()
        initEditTexts()
    }

    private fun changePhoneInputText(newText: String?, selectionIndex: Int) {
        binding.phoneEt.apply {
            setText(newText)
            setSelection(selectionIndex)
        }
    }

    private fun initButtons() {
        binding.startBtn.setOnClickListener {
            (requireActivity() as? BaseActivity)?.goToScreen(
                ActionType.Replace, QuestionnaireFragment.getInstance(binding.questionsCountEt.getText().toString())
            )
        }
    }


    private fun initEditTexts() {
        with(binding) {
            phoneEt.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(input: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(input: CharSequence?, start: Int, before: Int, count: Int) {

                    if (before > 0 && count == 0) {
                        return
                    }
                    when (input?.length) {
                        1 ->
                            changePhoneInputText("+7(9$input", 5)
                        6 ->
                            changePhoneInputText("$input)-", 8)
                        11 ->
                            changePhoneInputText("$input-", 12)
                        14 ->
                            changePhoneInputText("$input-", 15)
                        18 ->
                            changePhoneInputText(input.subSequence(0, input.length - 1).toString(), 17)
                    }
                }

                override fun afterTextChanged(p0: Editable?) {
                    val phoneRegex = """^\+7\(9\d{2}\)-\d{3}-\d{2}-\d{2}$""".toRegex()
                    val countRegex = "^[1-9]|10\$".toRegex()
                    val isValid = phoneRegex.matches(phoneEt.text.toString()) && countRegex.matches(questionsCountEt.text.toString())
                    startBtn.isEnabled = isValid
                    if (!isValid) {
                        val snack = Snackbar.make(binding.root, "Неверно введены данные", 1000)
                        (snack.view.layoutParams as? FrameLayout.LayoutParams)?.gravity = Gravity.CENTER
                        snack.show()
                    }
                }

            })

            questionsCountEt.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(input: CharSequence?, start: Int, before: Int, count: Int) {
                    if (input?.length == 3){
                        questionsCountEt.setText(input.subSequence(0, input.length - 1))
                        questionsCountEt.setSelection(2)
                    }
                }

                override fun afterTextChanged(p0: Editable?) {
                    val phoneRegex = """^\+7\(9\d{2}\)-\d{3}-\d{2}-\d{2}$""".toRegex()
                    val countRegex = "^[1-9]|10\$".toRegex()
                    val isValide = phoneRegex.matches(phoneEt.text.toString()) && countRegex.matches(questionsCountEt.text.toString())
                    startBtn.isEnabled = isValide
                    if (!isValide) {
                        Snackbar.make(binding.root, "Неверно введены данные", 1000).show()
                    }
                }
            })
        }
    }
}