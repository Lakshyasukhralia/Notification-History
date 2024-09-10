package com.sukhralia.notificationhistory.feature.tracker.domain.model

import android.graphics.drawable.Drawable

data class AppInfo(
    val appName: String,
    val packageName: String,
    val icon: Drawable
)