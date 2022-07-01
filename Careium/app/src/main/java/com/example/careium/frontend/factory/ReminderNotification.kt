package com.example.careium.frontend.factory

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.careium.R
import com.example.careium.frontend.home.activities.MainActivity

const val notificationID = 1
const val channelID = "channelID"
const val titleextra = "title"
const val messageextra ="message"

class ReminderNotification : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        val builder = NotificationCompat.Builder(context!!, channelID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(intent?.getStringExtra(titleextra))
            .setContentText(intent?.getStringExtra(messageextra))
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(notificationID, builder)

    }
}