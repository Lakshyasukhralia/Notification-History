package com.sukhralia.notificationhistory.feature.tracker.presentation.home

import com.sukhralia.notificationhistory.feature.tracker.domain.model.Notification

data class HomeState(
    var isLoading: Boolean = false,
    val notifications: List<Notification> = emptyList(),
    val groupedNotifications: Map<String?, List<Notification>> = emptyMap()
)