package com.example.mywanandroid.data.respository.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.mywanandroid.base.BaseApp
import com.example.mywanandroid.data.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

private val Context.userDataStore: DataStore<Preferences> by preferencesDataStore("user")

class UserDataStore private constructor(context: Context) {
    private val dataStore = context.applicationContext.userDataStore
    private var cacheUser: User? = null

    companion object {
        private val USER_NAME = stringPreferencesKey("user_name")
        private val USER_ID = longPreferencesKey("user_id")

        private var instance: UserDataStore? = null
        fun getInstance(context: Context = BaseApp.getInstance()): UserDataStore {
            if (instance == null) {
                instance = UserDataStore(context)
            }
            return instance!!
        }
    }

    suspend fun saveUser(user: User) {
        dataStore.edit { preferences ->
            preferences[USER_NAME] = user.username
            preferences[USER_ID] = user.userId
        }
        cacheUser = user
    }

    suspend fun saveUser(username: String, userId: Long) {
        dataStore.edit { preferences ->
            preferences[USER_NAME] = username
            preferences[USER_ID] = userId
        }
        cacheUser = User(username, userId)
    }

    fun getUserFlow(): Flow<User?> {
        return dataStore.data.map { preferences ->
            val username = preferences[USER_NAME]
            val userId = preferences[USER_ID]
            if (username != null && userId != null) {
                cacheUser = User(username, userId)
                cacheUser
            } else {
                null
            }
        }
    }

    fun getUser(): User? {
        cacheUser?.let { return@let it }
        cacheUser = runBlocking {
            val prefs = dataStore.data.first()
            val username = prefs[USER_NAME]
            val userId = prefs[USER_ID]
            if (username != null && userId != null) {
                User(username, userId)
            } else {
                null
            }
        }
        return cacheUser
    }

    fun isLogin(): Boolean {
        return runBlocking {
            dataStore.data.first()[USER_ID] != null
        }
    }

    suspend fun clear() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}