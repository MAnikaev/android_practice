package com.itis.homework.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.VISIBILITY_PRIVATE
import androidx.core.app.NotificationCompat.VISIBILITY_PUBLIC
import androidx.core.app.NotificationCompat.VISIBILITY_SECRET
import com.itis.homework.MainActivity
import com.itis.homework.R

object NotificationSender {
    fun sendNotification(
        ctx: Context,
        id: Int,
        importance: NotificationImportance,
        visibility: NotificationVisibility,
        title: String,
        body: String,
        isDetail: Boolean,
        isHaveButtons: Boolean
    ) {
        (ctx.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager)?.let { manager ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    ParamsKeys.NOTIFICATION_CHANNEL_ID,
                    ParamsKeys.NOTIFICATION_CHANNEL_NAME,
                    when(importance) {
                        NotificationImportance.High -> NotificationManager.IMPORTANCE_DEFAULT
                        NotificationImportance.Medium -> NotificationManager.IMPORTANCE_LOW
                        NotificationImportance.Urgent -> NotificationManager.IMPORTANCE_HIGH
                    })
                manager.createNotificationChannel(channel)
            }

            val intent = Intent(ctx, MainActivity::class.java)

            val pendingIntent = PendingIntent.getActivity(
                ctx,
                101,
                intent,
                PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
            )

            val notification = NotificationCompat.Builder(ctx, ParamsKeys.NOTIFICATION_CHANNEL_ID).apply {
                setSmallIcon(R.drawable.baseline_notifications_24)
                setContentTitle(title)
                setContentText(body)
                setAutoCancel(true)
                setContentIntent(pendingIntent)
                when(visibility) {
                    NotificationVisibility.Private -> setVisibility(VISIBILITY_PRIVATE)
                    NotificationVisibility.Public -> setVisibility(VISIBILITY_PUBLIC)
                    NotificationVisibility.Secret -> setVisibility(VISIBILITY_SECRET)
                }
                if(isDetail) {
                    setStyle(NotificationCompat.BigTextStyle()
                        .bigText(title))
                }
                if(isHaveButtons) {
                    val toastIntent = Intent(ctx, MainActivity::class.java)
                    toastIntent.putExtra(ParamsKeys.ACTION_KEY, ActionType.Toast.value)
                    val toastPendingIntent = PendingIntent.getActivity(
                        ctx,
                        101,
                        toastIntent,
                        PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_MUTABLE
                    )

                    val settingsIntent = Intent(ctx, MainActivity::class.java)
                    toastIntent.putExtra(ParamsKeys.ACTION_KEY, ActionType.SettingsNavigate.value)
                    val settingsPendingIntent = PendingIntent.getActivity(
                        ctx,
                        101,
                        settingsIntent,
                        PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_MUTABLE
                    )

                    addAction(R.drawable.baseline_waves_24, "Toast", toastPendingIntent)
                    addAction(R.drawable.baseline_edit_notifications_24, "Settings", settingsPendingIntent)
                }
            }


            manager.notify(id, notification.build())
        }
    }
}