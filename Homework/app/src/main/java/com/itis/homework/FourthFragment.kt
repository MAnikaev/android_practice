package com.itis.homework

import android.os.Bundle
import android.text.PrecomputedText.Params
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.itis.homework.databinding.FragmentFourthBinding

class FourthFragment : BaseFragment(R.layout.fragment_fourth) {

    private val binding: FragmentFourthBinding by viewBinding(FragmentFourthBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun initViews() {
        with(binding) {
            arguments?.let {
                firstTv.text = it.getString(ParamsKeys.FOURTH_FRAGMENT_FIRST_TITLE_KEY)
                secondTv.text = it.getString(ParamsKeys.FOURTH_FRAGMENT_SECOND_TITLE_KEY)
                thirdTv.text = it.getString(ParamsKeys.FOURTH_FRAGMENT_THIRD_TITLE_KEY)
            }
        }
    }

    companion object {
       const val FOURTH_FRAGMENT_TAG = "FOURTH_FRAGMENT_TAG"
        fun getInstance(firstTitle: String, secondTitle: String, thirdTitle: String) =
            FourthFragment().apply {
                arguments = Bundle().apply {
                    putString(ParamsKeys.FOURTH_FRAGMENT_FIRST_TITLE_KEY, firstTitle)
                    putString(ParamsKeys.FOURTH_FRAGMENT_SECOND_TITLE_KEY, secondTitle)
                    putString(ParamsKeys.FOURTH_FRAGMENT_THIRD_TITLE_KEY, thirdTitle)
                }
            }
    }
}