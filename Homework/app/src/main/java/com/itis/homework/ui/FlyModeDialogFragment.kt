package com.itis.homework.ui

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import by.kirich1409.viewbindingdelegate.dialogFragmentViewBinding
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.itis.homework.R
import com.itis.homework.databinding.DialogFragmentFlyModeBinding


class FlyModeDialogFragment : BottomSheetDialogFragment() {
    private val binding: DialogFragmentFlyModeBinding by viewBinding(DialogFragmentFlyModeBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_fragment_fly_mode, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calculateViewDialogHeight()
        isCancelable = false
    }

    private fun calculateViewDialogHeight() {
        val displayMetrics = requireContext().resources.displayMetrics
        val dialogHeight = displayMetrics.heightPixels / 3

        val layoutParams =
            FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .apply {
                    height = dialogHeight
                }

        binding.root.layoutParams = layoutParams
    }
    companion object {
        const val FLY_MODE_DIALOG_FRAGMENT_TAG = "FLY_MODE_DIALOG_FRAGMENT_TAG"
    }
}