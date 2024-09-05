package com.sukhralia.notificationhistory.feature.tracker.presentation.menu

data class MenuState(
    var isLoading: Boolean = false,
    val packages: List<String> = emptyList()
)