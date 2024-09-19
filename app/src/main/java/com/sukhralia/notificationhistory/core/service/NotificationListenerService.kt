package com.sukhralia.notificationhistory.core.service

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import com.sukhralia.notificationhistory.core.persistence.preference.PreferenceRepository
import com.sukhralia.notificationhistory.feature.tracker.domain.model.Notification
import com.sukhralia.notificationhistory.feature.tracker.domain.repository.NotificationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NotificationListenerService : NotificationListenerService(), KoinComponent {

    private val notificationRepository by inject<NotificationRepository>()
    private val preferenceRepository by inject<PreferenceRepository>()
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private var whiteListedApps: List<String>? = null
    private var lastStoredNotification: Notification? = null

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        val packageName = sbn.packageName
        val extras = sbn.notification.extras
        val title = extras.getString("android.title")
        val text = extras.getCharSequence("android.text")
        coroutineScope.launch {
            whiteListedApps = preferenceRepository.getString("white_listed_apps")?.split(",")

            if (whiteListedApps?.isNotEmpty() == true && !whiteListedApps!!.contains(packageName)) return@launch

            if (text.isNullOrEmpty() || title.isNullOrEmpty()) return@launch

            val modifiedTitle = title.split(":").getOrNull(0)?.split("(")?.getOrNull(0)?.trim()

            val notification = Notification(
                app = packageName,
                title = modifiedTitle,
                message = text.toString()
            )

            if (lastStoredNotification == notification) return@launch

            notificationRepository.saveNotifications(listOf(notification))

            lastStoredNotification = notification
            Log.d("NotificationListener", "Package: $packageName, Title: $title, Text: $text")
        }
    }

    override fun onListenerConnected() {
        val activeNotifications = activeNotifications
        activeNotifications?.forEach { sbn ->
            val packageName = sbn.packageName
            val extras = sbn.notification.extras
            val title = extras.getCharSequence("android.title")?.toString()
            val text = extras.getCharSequence("android.text").toString()

            Log.d(
                "NotificationListener",
                "Active Notification - Package: $packageName, Title: $title, Text: $text"
            )
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        Log.d("NotificationListener", "Notification removed from: ${sbn.packageName}")
    }
}