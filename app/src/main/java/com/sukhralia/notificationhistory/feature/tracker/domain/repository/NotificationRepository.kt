package com.sukhralia.notificationhistory.feature.tracker.domain.repository

import com.sukhralia.notificationhistory.feature.tracker.domain.model.Notification
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    suspend fun getNotificationsByPackage(packageName: String): Flow<List<Notification>>
    suspend fun getNotificationsByTitle(
        title: String,
        packageName: String
    ): Flow<List<Notification>>

    suspend fun saveNotifications(notifications: List<Notification>)
    suspend fun getAllPackages(): Flow<List<String>>
}