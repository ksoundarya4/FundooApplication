/**
 * Fundoo Notes
 * @description NotificationHelper class to create a notification
 * channel and notification manager.
 * @file NotificationHelper.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 29/02/2020
 */
package com.bridgelabz.fundoonotes.notification_util

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import androidx.core.app.NotificationCompat
import com.bridgelabz.fundoonotes.R

class NotificationHelper(base: Context) : ContextWrapper(base) {

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            createChannel()
    }

    private val notificationChannelId = "channel Note Id"
    private val notificationChannelName = "channel Note Name"
    private val notificationManager: NotificationManager =
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    @TargetApi(Build.VERSION_CODES.O)
    private fun createChannel() {
        val notificationChannel = NotificationChannel(
            notificationChannelId,
            notificationChannelName,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationChannel.enableLights(true)
        notificationChannel.enableVibration(true)
        notificationChannel.lightColor = R.color.PrimaryColor
        notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE

        getManager().createNotificationChannel(notificationChannel)
    }

    fun getManager(): NotificationManager {
        return notificationManager
    }

    fun getChannelNotification(title: String, message: String): NotificationCompat.Builder {
        return NotificationCompat.Builder(applicationContext, notificationChannelId).apply {
            setContentTitle(title)
            setContentText(message)
            setSmallIcon(R.drawable.ic_add_alert_grey)
        }
    }
}