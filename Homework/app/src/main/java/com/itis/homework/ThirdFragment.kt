package com.itis.homework

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.itis.homework.databinding.FragmentThirdBinding

class ThirdFragment : BaseFragment(R.layout.fragment_third) {

    private val binding by viewBinding(FragmentThirdBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun initViews() {
        with(binding) {
            val title = arguments?.getString(ParamsKeys.THIRD_FRAGMENT_TITLE_KEY)
            if(title?.isNotEmpty() == true){
                titleTv.text = title
            }
        }
    }
    companion object {
        const val THIRD_FRAGMENT_TAG = "THIRD_FRAGMENT_TAG"
        fun getInstance(title: String = "Третий фрагмент") =
            ThirdFragment().apply {
                arguments = Bundle().apply {
                    putString(ParamsKeys.THIRD_FRAGMENT_TITLE_KEY, title)
                }
            }
    }
}