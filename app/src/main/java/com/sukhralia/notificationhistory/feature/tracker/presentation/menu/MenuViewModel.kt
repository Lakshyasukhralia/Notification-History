package com.sukhralia.notificationhistory.feature.tracker.presentation.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sukhralia.notificationhistory.feature.tracker.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MenuViewModel : ViewModel(), KoinComponent {

    private val notificationRepo by inject<NotificationRepository>()

    private val _uiState = MutableStateFlow(MenuState())
    val uiState = _uiState.asStateFlow()

    init {
        getPackages()
    }

    fun getPackages() {
        viewModelScope.launch {
            notificationRepo.getAllPackages().collect { pkg ->
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        packages = pkg
                    )
                }
            }
        }
    }
}
