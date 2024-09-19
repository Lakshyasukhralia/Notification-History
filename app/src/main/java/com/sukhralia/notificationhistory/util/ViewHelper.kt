package com.sukhralia.notificationhistory.util

import com.sukhralia.notificationhistory.feature.tracker.domain.model.AppInfo

class ViewHelper {
    companion object {
        var appList: List<AppInfo> = emptyList()
//
//        fun allWhitelistedApps(): Boolean {
//            if (whiteListedApps == null) return true
//            val installedPkgs = appList.map { it.packageName }
//            return installedPkgs == whiteListedApps
//        }
    }
}