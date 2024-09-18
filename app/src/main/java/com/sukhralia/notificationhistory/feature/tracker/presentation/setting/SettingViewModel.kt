package com.sukhralia.notificationhistory.feature.tracker.presentation.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sukhralia.notificationhistory.core.persistence.preference.PreferenceRepository
import com.sukhralia.notificationhistory.util.ViewHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SettingViewModel : ViewModel(), KoinComponent {

    private val preferenceRepository by inject<PreferenceRepository>()

    private val _uiState = MutableStateFlow(SettingState())
    val uiState = _uiState.asStateFlow()

    init {
        getWhiteListedApps()
    }

    fun removeAll() {
        viewModelScope.launch {
            preferenceRepository.putString("white_listed_apps", "")
            ViewHelper.whiteListedApps = emptyList()
            _uiState.update { state ->
                state.copy(
                    whiteListedApps = emptyList()
                )
            }
        }
    }

    fun addAll() {
        viewModelScope.launch {
            val appList = _uiState.value.installedApps.map { it.packageName }
            preferenceRepository.putString("white_listed_apps", appList.joinToString(","))
            ViewHelper.whiteListedApps = appList

            _uiState.update { state ->
                state.copy(
                    whiteListedApps = appList
                )
            }
        }
    }

    fun updateWhiteListedApps(pkg: String) {
        viewModelScope.launch {
            val wApps = _uiState.value.whiteListedApps.toMutableList()
            if (wApps.isEmpty().not() && wApps.contains(pkg)) {
                wApps.remove(pkg)
            } else {
                wApps.add(pkg)
            }
            preferenceRepository.putString("white_listed_apps", wApps.joinToString(","))
            ViewHelper.whiteListedApps = wApps
            _uiState.update { state ->
                state.copy(
                    whiteListedApps = wApps
                )
            }
        }
    }

    private fun getWhiteListedApps() {
        viewModelScope.launch {
            val wApps = preferenceRepository.getString("white_listed_apps")
            if (wApps == null) {
                val appList = _uiState.value.installedApps.map { it.packageName }
                preferenceRepository.putString(
                    "white_listed_apps",
                    appList.joinToString(",")
                )
                _uiState.update { state ->
                    state.copy(
                        whiteListedApps = appList
                    )
                }
                ViewHelper.whiteListedApps = appList
            } else {
                val wAppsList = wApps.split(",").toMutableList()
                _uiState.update { state ->
                    state.copy(
                        whiteListedApps = wAppsList
                    )
                }
                ViewHelper.whiteListedApps = wAppsList
            }
        }
    }

}
