package com.itis.homework

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.itis.homework.databinding.FragmentSecondBinding

class SecondFragment : BaseFragment(R.layout.fragment_second) {

    private val binding by viewBinding(FragmentSecondBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initButtons()
    }

    private fun initViews() {
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
                bnd.firstBtn.setOnClickListener {
                    val text = bnd.titleTv.text.toString()
                    act.goToScreen(
                        action = ActionType.Replace,
                        destination = ThirdFragment.getInstance(text),
                        isAddToBackStack = false
                    )
                    act.goToScreen(
                        action = ActionType.Replace,
                        destination = FirstFragment.getInstance("Первый фрагмент", text),
                        isAddToBackStack = true
                    )
                    act.goToScreen(
                        action = ActionType.Replace,
                        destination = ThirdFragment.getInstance(text),
                        isAddToBackStack = true
                    )
                }
                bnd.secondBtn.setOnClickListener {
                    act.goToScreen(
                        action = ActionType.Replace,
                        destination = FirstFragment.getInstance("Первый фрагмент"),
                        isAddToBackStack = true
                    )
                }
            }
        }
    }
    companion object {
        const val SECOND_FRAGMENT_TAG = "SECOND_FRAGMENT_TAG"
        fun getInstance(title: String?) =
            SecondFragment().apply {
                arguments = Bundle().apply {
                    putString(ParamsKeys.SECOND_FRAGMENT_TITLE_KEY, title)
                }
            }
    }
}