package com.sukhralia.notificationhistory.util

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.provider.Settings
import android.text.TextUtils
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.sukhralia.notificationhistory.feature.tracker.domain.model.AppInfo
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun isNotificationServiceEnabled(context: Context): Boolean {
    val enabledListeners =
        Settings.Secure.getString(context.contentResolver, "enabled_notification_listeners")
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

fun getInstalledApps(context: Context): List<AppInfo> {
    val apps = mutableListOf<AppInfo>()
    val packageManager: PackageManager = context.packageManager
    val packages: List<PackageInfo> =
        packageManager.getInstalledPackages(PackageManager.GET_META_DATA)

    for (packageInfo in packages) {
        val appName: String = packageInfo.applicationInfo.loadLabel(packageManager).toString()
        val packageName: String = packageInfo.packageName
        val icon: Drawable = packageInfo.applicationInfo.loadIcon(packageManager)
        apps.add(AppInfo(appName, packageName, icon))
    }

    return apps
}

fun getAppIcon(context: Context, icon: Int): Drawable? {
    return ContextCompat.getDrawable(context, icon)
}