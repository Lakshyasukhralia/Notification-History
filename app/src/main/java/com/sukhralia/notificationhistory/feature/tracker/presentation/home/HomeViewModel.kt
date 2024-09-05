package com.sukhralia.notificationhistory.feature.tracker.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sukhralia.notificationhistory.feature.tracker.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomeViewModel : ViewModel(), KoinComponent {

    private val notificationRepo by inject<NotificationRepository>()

    private val _uiState = MutableStateFlow(HomeState())
    val uiState = _uiState.asStateFlow()

    fun getNotificationsByPackage(packageName: String) {
        viewModelScope.launch {
            notificationRepo.getNotificationsByPackage(packageName).collect { notifications ->
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        notifications = notifications,
                        groupedNotifications = notifications
                            .filter { it.title != null }
                            .groupBy { it.title }
                    )
                }
            }
        }
    }

}