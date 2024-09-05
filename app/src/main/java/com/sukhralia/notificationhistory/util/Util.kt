package com.sukhralia.notificationhistory.util

import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.text.TextUtils
import androidx.core.content.ContextCompat.startActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun isNotificationServiceEnabled(context: Context): Boolean {
    val enabledListeners = Settings.Secure.getString(context.contentResolver, "enabled_notification_listeners")
    val packageName = context.packageName

    return !TextUtils.isEmpty(enabledListeners) && enabledListeners.contains(packageName)
}

fun openNotificationSettings(context: Context) {
    val intent = Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
    startActivity(context, intent, null)
}

fun checkAllowedPackageName(packageName: String) =
    packageName == "com.whatsapp"

fun formatDateTime(date: Date): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    return formatter.format(date)
}