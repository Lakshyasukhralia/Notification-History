package com.sukhralia.notificationhistory.util

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import com.sukhralia.notificationhistory.feature.tracker.domain.model.AppInfo
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
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

fun saveTextFileToAppSpecificExternalStorage(
    context: Context,
    fileName: String,
    stringList: List<String>
): File? {
    return try {
        val file = File(context.getExternalFilesDir(null), fileName)

        val fileOutputStream = FileOutputStream(file)
        stringList.forEach { line ->
            fileOutputStream.write((line + "\n").toByteArray())
        }

        fileOutputStream.close()
        Log.d("File", "File saved successfully at: ${file.absolutePath}")
        file
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}

fun saveFileToDownloads(context: Context, fileName: String, content: List<String>) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, "text/plain")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
        }

        val resolver = context.contentResolver
        val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)

        uri?.let {
            try {
                val outputStream: OutputStream? = resolver.openOutputStream(uri)
                outputStream?.use { stream ->
                    content.forEach { line ->
                        stream.write((line + "\n").toByteArray())
                    }
                }
                Log.d("File", "File saved successfully to Downloads")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}