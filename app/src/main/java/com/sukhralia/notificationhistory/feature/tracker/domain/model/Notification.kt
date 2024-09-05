package com.sukhralia.notificationhistory.feature.tracker.domain.model

import com.sukhralia.notificationhistory.feature.tracker.data.local.entity.NotificationEntity
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class Notification
    (
    val app: String? = null,
    val title: String? = null,
    val message: String? = null,
    val createDate: Long? = null
)

fun Notification.toNotificationEntity() = NotificationEntity(
    app = app, title = title, message = message
)