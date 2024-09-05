package com.sukhralia.notificationhistory.feature.tracker.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sukhralia.notificationhistory.feature.tracker.domain.model.Notification
import java.util.Date

@Entity("notification_history")
data class NotificationEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(defaultValue = "0") val uid: Int? = null,
    @ColumnInfo(name = "app") val app: String? = null,
    @ColumnInfo(name = "title") val title: String? = null,
    @ColumnInfo(name = "message") val message: String? = null,
    @ColumnInfo(name = "create_date") val createDate: Date = Date(),
) : java.io.Serializable

fun NotificationEntity.toNotification() = Notification(
    app = app,
    title = title,
    message = message,
    createDate = createDate.time
)

