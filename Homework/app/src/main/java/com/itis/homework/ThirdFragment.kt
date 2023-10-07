package com.itis.homework

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.itis.homework.databinding.FragmentThirdBinding

class ThirdFragment : BaseFragment(R.layout.fragment_third) {

    private val binding by viewBinding(FragmentThirdBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_third, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        with(binding) {
            val title = arguments?.getString(ParamsKeys.THIRD_FRAGMENT_TITLE_KEY)
                titleTv.text = title
        }
    }
    companion object {
        const val THIRD_FRAGMENT_TAG = "THIRD_FRAGMENT_TAG"
        fun getInstance(title: String) =
            ThirdFragment().apply {
                arguments = Bundle().apply {
                    putString(ParamsKeys.THIRD_FRAGMENT_TITLE_KEY, title)
                }
            }
    }
}