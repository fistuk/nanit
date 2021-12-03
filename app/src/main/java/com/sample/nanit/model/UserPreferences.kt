package com.sample.nanit.model

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore by preferencesDataStore(name = "settings")

@Singleton
class UserPreferences @Inject constructor(private val context: Context) {
    private val PHOTO_PATH = stringPreferencesKey("photo_path")
    private val BABY_NAME = stringPreferencesKey("baby_name")
    private val BABY_BIRTH_DAY = longPreferencesKey("baby_birth_day")

    suspend fun getPhoto(): String? {
        return context.dataStore.data.firstOrNull()?.get(PHOTO_PATH)
    }

    suspend fun getName(): String? {
        return context.dataStore.data.firstOrNull()?.get(BABY_NAME)
    }

    suspend fun getBirthData(): Long? {
        return context.dataStore.data.firstOrNull()?.get(BABY_BIRTH_DAY)
    }

    suspend fun setPhotoPath(path: String) {
        context.dataStore.edit { preferences ->
            preferences[PHOTO_PATH] = path
        }
    }

    suspend fun setName(name: String) {
        context.dataStore.edit { preferences ->
            preferences[BABY_NAME] = name
        }
    }

    suspend fun setBirthdate(date: Long) {
        context.dataStore.edit { preferences ->
            preferences[BABY_BIRTH_DAY] = date
        }
    }
}