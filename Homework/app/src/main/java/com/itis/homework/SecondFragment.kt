package com.itis.homework

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.itis.homework.databinding.FragmentSecondBinding

class SecondFragment : BaseFragment(R.layout.fragment_second) {

    private val binding by viewBinding(FragmentSecondBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initButtons()
    }

    override fun initViews() {
        with(binding) {
            val title = arguments?.getString(ParamsKeys.SECOND_FRAGMENT_TITLE_KEY)
            if(title?.isNotEmpty() == true){
                titleTv.text = title
            }
        }
    }
    private fun initButtons() {
        binding.let {bnd ->
            (requireActivity() as? BaseActivity)?.let {act ->
                val text = bnd.titleTv.text.toString()
                var firstFragmentTitleText = ""
                var thirdFragmentTitleText = ""
                if(text != "Второй фрагмент") {
                    thirdFragmentTitleText = text
                    firstFragmentTitleText = text
                } else {
                    thirdFragmentTitleText = "Третий фрагмент"
                }
                bnd.firstBtn.setOnClickListener {
                    act.goToScreen(
                        action = ActionType.Replace,
                        destination = ThirdFragment.getInstance(thirdFragmentTitleText),
                        isAddToBackStack = false
                    )
                    act.goToScreen(
                        action = ActionType.Replace,
                        destination = FirstFragment.getInstance("Первый фрагмент", firstFragmentTitleText),
                        isAddToBackStack = true
                    )
                    act.goToScreen(
                        action = ActionType.Replace,
                        destination = ThirdFragment.getInstance(thirdFragmentTitleText),
                        isAddToBackStack = true
                    )
                }
                bnd.secondBtn.setOnClickListener {
                    act.goToScreen(
                        action = ActionType.Replace,
                        destination = FirstFragment.getInstance("Первый фрагмент", firstFragmentTitleText),
                        isAddToBackStack = true
                    )
                }
            }
        }
    }
    companion object {
        const val SECOND_FRAGMENT_TAG = "SECOND_FRAGMENT_TAG"
        fun getInstance(title: String? = "Второй фрагмент") =
            SecondFragment().apply {
                arguments = Bundle().apply {
                    putString(ParamsKeys.SECOND_FRAGMENT_TITLE_KEY, title)
                }
            }
    }
}