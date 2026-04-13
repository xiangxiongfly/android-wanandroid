package com.example.mywanandroid.data.respository.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.mywanandroid.base.BaseApp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

private val Context.userDataStore: DataStore<Preferences> by preferencesDataStore("cookie")

class CookieDataStore private constructor(context: Context) {
    private val dataStore = context.applicationContext.userDataStore

    companion object {
        private val COOKIE_SET = stringSetPreferencesKey("cookieSet")

        private var instance: CookieDataStore? = null
        fun getInstance(context: Context = BaseApp.getInstance()): CookieDataStore {
            if (instance == null) {
                instance = CookieDataStore(context)
            }
            return instance!!
        }
    }

    suspend fun saveCookieSet(set: Set<String>) {
        dataStore.edit { preferences ->
            preferences[COOKIE_SET] = set
        }
    }

    fun getCookieSetFlow(): Flow<Set<String>?> {
        return dataStore.data.map { preferences ->
            val cookieSet = preferences[COOKIE_SET]
            cookieSet
        }
    }

    fun getCookieSet(): Set<String>? {
        return runBlocking {
            val prefs = dataStore.data.first()
            val cookieSet = prefs[COOKIE_SET]
            cookieSet
        }
    }

    suspend fun clear() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}