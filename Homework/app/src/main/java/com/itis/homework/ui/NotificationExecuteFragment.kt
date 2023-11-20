package com.itis.homework.ui

import android.app.Notification
import android.app.NotificationManager
import android.content.ContentResolver
import android.content.res.Configuration
import android.os.Bundle
import android.provider.Settings
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
import com.itis.homework.utils.ParamsKeys
import java.util.UUID
import kotlin.random.Random


class NotificationExecuteFragment : Fragment(R.layout.fragment_notification_execute) {
    private val binding: FragmentNotificationExecuteBinding by viewBinding(FragmentNotificationExecuteBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.sendNotificationBtn.setOnClickListener {
            val notificationTitle = binding.notificationTitleEt.text.toString()
            val notificationDesc = binding.notificationBodyEt.text.toString()
            val settings = getNotificationSettings()
//            NotificationSender.sendNotification(
//                ctx = requireContext(),
//                id = Random.nextInt(-1000000, 1000000),
//                importance = settings["importance"] as NotificationImportance,
//                visibility = settings["visibility"] as NotificationVisibility,
//                title = notificationTitle,
//                body = notificationDesc,
//                isDetail = settings["isDetail"] as Boolean,
//                isHaveButtons = settings["isHaveButtons"] as Boolean
//            )
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

    private fun getNotificationSettings() : Map<String, Any> {
        val notificationSettings = mutableMapOf<String, Any>()
        setDefaultSettings(notificationSettings)
        parentFragmentManager.setFragmentResultListener(ParamsKeys.SETTINGS_FRAGMENT_RESULT_KEY, this.viewLifecycleOwner) { _, result ->
            notificationSettings["importance"] = when (result.getInt(ParamsKeys.IMPORTANCE_SETTING_KEY)) {
                R.id.importance_high_rb -> NotificationImportance.High
                R.id.importance_medium_rb -> NotificationImportance.Medium
                R.id.importance_urgent_rb -> NotificationImportance.Urgent
                else -> NotificationImportance.Medium
            }
            notificationSettings["visibility"] = when(result.getInt(ParamsKeys.VISIBILITY_SETTING_KEY)) {
                R.id.privacy_private_rb -> NotificationVisibility.Private
                R.id.privacy_public_rb -> NotificationVisibility.Public
                R.id.privacy_secret_rb -> NotificationVisibility.Secret
                else -> NotificationVisibility.Public
            }
            notificationSettings["isHaveButtons"] = result.getBoolean(ParamsKeys.IS_HAVE_BUTTONS_SETTING_KEY)
            notificationSettings["isDetail"] = result.getBoolean(ParamsKeys.IS_DETAIL_SETTING_KEY)
        }
        return notificationSettings.toMap()
    }

    private fun setDefaultSettings(notificationSettings: MutableMap<String, Any>) {
        notificationSettings["importance"] = NotificationImportance.Medium
        notificationSettings["visibility"] = NotificationVisibility.Public
        notificationSettings["isHaveButtons"] = false
        notificationSettings["isDetail"] = false
    }
}