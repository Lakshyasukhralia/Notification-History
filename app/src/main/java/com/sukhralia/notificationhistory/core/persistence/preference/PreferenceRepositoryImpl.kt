package com.sukhralia.notificationhistory.core.persistence.preference


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import org.koin.core.component.KoinComponent

private const val PREFERENCES_NAME = "app_preferences"

val Context.preferencesDataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

class PreferenceRepositoryImpl (
    private val context: Context
) : PreferenceRepository, KoinComponent {

    override suspend fun putString(key: String, value: String) {
        val preferencesKey = stringPreferencesKey(key)
        context.preferencesDataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    override suspend fun putInt(key: String, value: Int) {
        val preferencesKey = intPreferencesKey(key)
        context.preferencesDataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    override suspend fun getString(key: String): String? {
        return try {
            val preferencesKey = stringPreferencesKey(key)
            val preferences = context.preferencesDataStore.data.first()
            preferences[preferencesKey]
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun getInt(key: String): Int? {
        return try {
            val preferencesKey = intPreferencesKey(key)
            val preferences = context.preferencesDataStore.data.first()
            preferences[preferencesKey]
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun putBoolean(key: String, value: Boolean) {
        val preferencesKey = booleanPreferencesKey(key)
        context.preferencesDataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    override suspend fun getBoolean(key: String, default: Boolean): Boolean {
        return try {
            val preferencesKey = booleanPreferencesKey(key)
            val preferences = context.preferencesDataStore.data.first()
            preferences[preferencesKey] ?: default
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun clearAll() {
        context.preferencesDataStore.edit { it.clear() }
    }
}
