package com.sukhralia.notificationhistory.core.di

import androidx.room.Room
import com.sukhralia.notificationhistory.core.persistence.db.Database
import com.sukhralia.notificationhistory.feature.tracker.data.local.dao.NotificationDao
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

val databaseModule: () -> Module
    get() = {
        module {
            single {
                Room.databaseBuilder(
                    androidApplication(),
                    Database::class.java, "notification_history_db"
                )
                    .build()
            }
            single<NotificationDao> {
                val database = get<Database>()
                database.notificationDao()
            }
        }
    }

