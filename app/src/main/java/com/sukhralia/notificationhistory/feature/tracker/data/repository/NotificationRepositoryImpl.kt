package com.sukhralia.notificationhistory.feature.tracker.data.repository

import com.sukhralia.notificationhistory.feature.tracker.data.local.dao.NotificationDao
import com.sukhralia.notificationhistory.feature.tracker.data.local.entity.toNotification
import com.sukhralia.notificationhistory.feature.tracker.domain.model.Notification
import com.sukhralia.notificationhistory.feature.tracker.domain.model.toNotificationEntity
import com.sukhralia.notificationhistory.feature.tracker.domain.repository.NotificationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent

class NotificationRepositoryImpl(private val notificationDao: NotificationDao) :
    NotificationRepository, KoinComponent {

    override suspend fun getAllPackages(): Flow<List<String>> {
        return notificationDao.getAllPackages()
    }

    override suspend fun saveNotifications(notifications: List<Notification>) {
        withContext(Dispatchers.IO) {
            notificationDao.insertAll(*notifications.map { it.toNotificationEntity() }
                .toTypedArray())
        }
    }

    override suspend fun getNotificationsByPackage(packageName: String): Flow<List<Notification>> {
        return notificationDao.getAllByPackageName(packageName).map { notificationEntityList ->
            notificationEntityList.map { it.toNotification() }
        }
    }

    override suspend fun getNotificationsByTitle(
        title: String,
        packageName: String
    ): Flow<List<Notification>> {
        return notificationDao.getNotificationByTitle(title, packageName)
            .map { notificationEntityList ->
                notificationEntityList.map { it.toNotification() }
            }
    }
}