package com.itis.hw.ui.fragments

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.itis.hw.R
import com.itis.hw.databinding.DialogDataChangeFragmentBinding
import com.itis.hw.di.ServiceLocator
import com.itis.hw.models.Session
import com.itis.hw.utils.Hasher
import com.itis.hw.utils.ParamKeys
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DialogDataChangeFragment : BottomSheetDialogFragment(R.layout.dialog_data_change_fragment) {

    private val binding: DialogDataChangeFragmentBinding by viewBinding(DialogDataChangeFragmentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calculateViewDialogHeight()

        initButtons()
    }

    private fun calculateViewDialogHeight() {
        val displayMetrics = requireContext().resources.displayMetrics
        val dialogHeight = displayMetrics.heightPixels / 2

        val layoutParams =
            FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .apply {
                    height = dialogHeight
                }

        binding.root.layoutParams = layoutParams
    }

    private fun initButtons() {
        with(binding) {
            confirmBtn.setOnClickListener {
                val newPhone = changePhoneEt.text.toString()
                val newPassword = changePasswordEt.text.toString()

                val sessionString = ServiceLocator.getPrefs().getString(ParamKeys.SESSION_KEY, null)
                val userEmail = ServiceLocator.getGson().fromJson(sessionString, Session::class.java).userEmail

                lifecycleScope.launch(Dispatchers.IO) {
                    if(newPhone.isNotEmpty()) {
                        ServiceLocator.getDbInstance().userDao().updateUserPhone(userEmail, newPhone)
                    }
                    if(newPassword.isNotEmpty()) {
                        ServiceLocator.getDbInstance().userDao().updateUserPassword(userEmail, Hasher.getHash(newPassword))
                    }
                }

                parentFragmentManager.setFragmentResult(ParamKeys.CHANGE_DATA_DIALOG_FRAGMENT_RESULT_KEY, Bundle().apply {

                    putString(ParamKeys.NEW_PHONE_KEY, newPhone)
                })
                dismiss()
            }
        }
    }


    companion object {
        const val DIALOG_DATA_CHANGE_FRAGMENT_TAG = "DIALOG_DATA_CHANGE_FRAGMENT_TAG"

        fun getInstance() = DialogDataChangeFragment()
    }
}