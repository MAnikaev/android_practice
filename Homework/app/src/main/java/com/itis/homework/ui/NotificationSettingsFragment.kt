package com.itis.homework.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.itis.homework.R
import com.itis.homework.databinding.FragmentNotificationSettingsBinding
import com.itis.homework.utils.ParamsKeys


class NotificationSettingsFragment : Fragment(R.layout.fragment_notification_settings) {
    private val binding: FragmentNotificationSettingsBinding by viewBinding(FragmentNotificationSettingsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.confirmSettingsBtn.setOnClickListener {
            parentFragmentManager.setFragmentResult(ParamsKeys.SETTINGS_FRAGMENT_RESULT_KEY, Bundle().apply {
                with(binding) {
                    putInt(ParamsKeys.IMPORTANCE_SETTING_KEY, importanceRg.checkedRadioButtonId)
                    putInt(ParamsKeys.VISIBILITY_SETTING_KEY, privacyRg.checkedRadioButtonId)
                    putBoolean(ParamsKeys.IS_DETAIL_SETTING_KEY, detailingCb.isChecked)
                    putBoolean(ParamsKeys.IS_HAVE_BUTTONS_SETTING_KEY, buttonsCb.isChecked)
                }
            })
        }
    }
}