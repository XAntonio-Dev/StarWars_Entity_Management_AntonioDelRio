package com.example.starwarsentitymanagementsystem.ui.helper;

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.example.starwarsentitymanagementsystem.R
import kotlin.random.Random

class NotificationHandler(private val context: Context) {

    private val notificationManager =
        context.getSystemService(NotificationManager::class.java)

    private val notificationChannelID = "planet_channel_id"

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            notificationChannelID,
            "Planetas",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Notificaciones de creación de planetas"
        }

        notificationManager.createNotificationChannel(channel)
    }

    fun showSimpleNotification(contentTitle: String, contentText: String) {
        val notification = NotificationCompat.Builder(context, notificationChannelID)
            .setContentTitle(contentTitle)
            .setContentText(contentText)
            .setSmallIcon(R.drawable.baseline_notifications)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(Random.nextInt(), notification)
    }
}