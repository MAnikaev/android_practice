package com.itis.homework.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.itis.homework.R
import com.itis.homework.databinding.FragmentNotificationSettingsBinding


class NotificationSettingsFragment : Fragment(R.layout.fragment_notification_settings) {
    val binding: FragmentNotificationSettingsBinding by viewBinding(FragmentNotificationSettingsBinding::bind)
}