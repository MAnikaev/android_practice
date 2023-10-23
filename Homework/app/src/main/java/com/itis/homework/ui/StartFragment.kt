package com.itis.homework.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
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

                    if (input?.length == 1 && !(before > 0 && count == 0)) {
                        changePhoneInputText("+7(9$input", 5)
                    }
                    else if (input?.length == 6 && !(before > 0 && count == 0)) {
                        changePhoneInputText("$input)-", 8)
                    }
                    else if (input?.length == 11 && !(before > 0 && count == 0)) {
                        changePhoneInputText("$input-", 12)
                    }
                    else if (input?.length == 14 && !(before > 0 && count == 0)) {
                        changePhoneInputText("$input-", 15)
                    }
                    else if (input?.length == 18 && !(before > 0 && count == 0)) {
                        changePhoneInputText(input.subSequence(0, input.length - 1).toString(), 17)
                    }
                }

                override fun afterTextChanged(p0: Editable?) {
                    val phoneRegex = """^\+7\(9\d{2}\)-\d{3}-\d{2}-\d{2}$""".toRegex()
                    val countRegex = "^[1-9]|10\$".toRegex()
                    startBtn.isEnabled = phoneRegex.matches(phoneEt.text.toString()) && countRegex.matches(questionsCountEt.text.toString())
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
                    startBtn.isEnabled = phoneRegex.matches(phoneEt.text.toString()) && countRegex.matches(questionsCountEt.text.toString())
                }
            })
        }
    }
}