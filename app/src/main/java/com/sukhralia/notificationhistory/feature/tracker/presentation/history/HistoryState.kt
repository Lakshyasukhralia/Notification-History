package com.sukhralia.notificationhistory.feature.tracker.presentation.history

import com.sukhralia.notificationhistory.feature.tracker.domain.model.Notification

data class HistoryState(
    var isLoading: Boolean = false,
    val notifications: List<Notification> = emptyList(),
)