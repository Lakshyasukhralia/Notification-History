package com.sukhralia.notificationhistory.core.di

import com.sukhralia.notificationhistory.core.persistence.preference.PreferenceRepository
import com.sukhralia.notificationhistory.core.persistence.preference.PreferenceRepositoryImpl
import org.koin.core.module.Module
import org.koin.dsl.module

val prefModule: () -> Module
    get() = {
        module {
            single<PreferenceRepository> { PreferenceRepositoryImpl(get()) }
        }
    }