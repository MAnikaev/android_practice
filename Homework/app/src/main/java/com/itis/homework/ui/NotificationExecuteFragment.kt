package com.itis.homework.ui

import android.app.Notification
import android.app.NotificationManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import by.kirich1409.viewbindingdelegate.viewBinding
import com.itis.homework.R
import com.itis.homework.databinding.FragmentNotificationExecuteBinding
import com.itis.homework.utils.NotificationImportance
import com.itis.homework.utils.NotificationSender
import com.itis.homework.utils.NotificationVisibility
import java.util.UUID
import kotlin.random.Random


class NotificationExecuteFragment : Fragment(R.layout.fragment_notification_execute) {
    private val binding: FragmentNotificationExecuteBinding by viewBinding(FragmentNotificationExecuteBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.sendNotificationBtn.setOnClickListener {
            val notificationTitle = binding.notificationTitleEt.text.toString()
            val notificationDesc = binding.notificationBodyEt.text.toString()
            NotificationSender.sendNotification(
                ctx = requireContext(),
                id = Random.nextInt(-1000000, 1000000),
                importance = NotificationImportance.Urgent,
                visibility = NotificationVisibility.Public,
                title = notificationTitle,
                body = notificationDesc,
                isDetail = false,
                isHaveButtons = true
            )
        }
    }
}