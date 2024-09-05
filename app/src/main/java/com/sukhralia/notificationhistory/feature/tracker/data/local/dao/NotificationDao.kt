package com.sukhralia.notificationhistory.feature.tracker.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.sukhralia.notificationhistory.feature.tracker.data.local.entity.NotificationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {
    @Query(" SELECT DISTINCT app FROM notification_history")
    fun getAllPackages(): Flow<List<String>>

    @Query("SELECT * FROM notification_history WHERE app = :packageName ORDER BY create_date DESC")
    fun getAllByPackageName(packageName: String): Flow<List<NotificationEntity>>

    @Query("SELECT * FROM notification_history WHERE title LIKE :title || '%' AND app = :packageName ORDER BY create_date DESC")
    fun getNotificationByTitle(title: String, packageName: String): Flow<List<NotificationEntity>>

    @Insert
    fun insertAll(vararg notificationEntities: NotificationEntity)

    @Delete
    fun delete(notificationEntity: NotificationEntity)

    @Query("DELETE FROM notification_history")
    fun clearAll()
}