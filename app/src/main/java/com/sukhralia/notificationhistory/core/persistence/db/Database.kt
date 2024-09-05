package com.sukhralia.notificationhistory.core.persistence.db

import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.Database
import com.sukhralia.notificationhistory.core.persistence.db.converter.Converters
import com.sukhralia.notificationhistory.feature.tracker.data.local.dao.NotificationDao
import com.sukhralia.notificationhistory.feature.tracker.data.local.entity.NotificationEntity

@Database(entities = [NotificationEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class Database : RoomDatabase() {

    abstract fun notificationDao(): NotificationDao

}