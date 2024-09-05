package com.sukhralia.notificationhistory.feature.tracker.di

import com.sukhralia.notificationhistory.feature.tracker.data.repository.NotificationRepositoryImpl
import com.sukhralia.notificationhistory.feature.tracker.domain.repository.NotificationRepository
import org.koin.core.module.Module
import org.koin.dsl.module

val homeModule: () -> Module
    get() = {
        module {
            single<NotificationRepository> { NotificationRepositoryImpl(get()) }
        }
    }