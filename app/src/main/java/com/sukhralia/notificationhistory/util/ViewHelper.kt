package com.sukhralia.notificationhistory.util

import com.sukhralia.notificationhistory.feature.tracker.domain.model.AppInfo

class ViewHelper {
    companion object {
        var appList: List<AppInfo> = emptyList()
        var whiteListedApps: List<String>? = null
    }
}