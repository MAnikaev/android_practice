package com.itis.homework.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.itis.homework.R
import com.itis.homework.databinding.FragmentNotificationExecuteBinding


class NotificationExecuteFragment : Fragment(R.layout.fragment_notification_execute) {
    val binding: FragmentNotificationExecuteBinding by viewBinding(FragmentNotificationExecuteBinding::bind)
}