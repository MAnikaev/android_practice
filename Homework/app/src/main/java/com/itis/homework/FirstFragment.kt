package com.itis.homework

import android.os.Bundle
import android.util.Log
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.itis.homework.databinding.FragmentFirstBinding

class FirstFragment : BaseFragment(R.layout.fragment_first) {

    private val binding by viewBinding(FragmentFirstBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initButtons()
    }

    override fun initViews() {
        with(binding) {
            titleTv.text = arguments?.getString(ParamsKeys.FIRST_FRAGMENT_TITLE_KEY)
            mainEt.setText(arguments?.getString(ParamsKeys.FIRST_FRAGMENT_EDIT_TEXT_TEXT_KEY))
        }
    }

    private fun initButtons(){
        binding.let {bnd ->
            bnd.goNextBtn.setOnClickListener {
                (requireActivity() as? BaseActivity)?.let {
                    val text = bnd.mainEt.text.toString()
                    val secondFragment = SecondFragment.getInstance(text)
                    val thirdFragment = ThirdFragment.getInstance(text)
                    it.goToScreen(
                        action = ActionType.Replace,
                        destination = secondFragment,
                        isAddToBackStack = true
                    )
                    it.goToScreen(
                        action = ActionType.Replace,
                        destination = thirdFragment,
                        isAddToBackStack = true
                    )
                }
            }
        }
    }
    companion object {
        const val FIRST_FRAGMENT_TAG = "FIRST_FRAGMENT_TAG"
        fun getInstance(titleText: String = "Первый фрагмент", editTextText: String = "") =
            FirstFragment().apply {
                arguments = Bundle().apply {
                    putString(ParamsKeys.FIRST_FRAGMENT_TITLE_KEY, titleText)
                    putString(ParamsKeys.FIRST_FRAGMENT_EDIT_TEXT_TEXT_KEY, editTextText)
                }
            }
    }
}