package com.sukhralia.notificationhistory.feature.tracker.presentation.setting

import com.sukhralia.notificationhistory.feature.tracker.domain.model.AppInfo
import com.sukhralia.notificationhistory.util.ViewHelper

data class SettingState(
    val installedApps: List<AppInfo> = ViewHelper.appList,
    val whiteListedApps: List<String>? = null,
)