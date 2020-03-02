/**
 * Fundoo Notes
 * @description AlertReceiver extends BroadcastReceiver
 * which delivers notification when AlertReceiver is called.
 * @file AlertReceiver.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 02/03/2020
 */
package com.bridgelabz.fundoonotes.notification_util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlertReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {

        val notificationHelper = NotificationHelper(context)
        val notificationBuilder =
            notificationHelper.getChannelNotification("Note Reminder", "Display note notification")
        notificationHelper.getManager().notify(1, notificationBuilder.build())
    }
}