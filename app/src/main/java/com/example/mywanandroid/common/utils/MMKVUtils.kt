package com.example.mywanandroid.common.utils

import android.content.Context
import android.util.Log
import com.tencent.mmkv.MMKV

object MMKVUtils {
    private val mmkv by lazy { MMKV.defaultMMKV() }

    fun init(context: Context) {
        MMKV.initialize(context)
    }

    fun put(key: String, value: Any?) {
        Log.e("TAG", "key= ${key}")
        Log.e("TAG", "value= ${value}")
        when (value) {
            is String -> mmkv.encode(key, value)
            is Int -> mmkv.encode(key, value)
            is Long -> mmkv.encode(key, value)
            is Float -> mmkv.encode(key, value)
            is Double -> mmkv.encode(key, value)
            is Boolean -> mmkv.encode(key, value)
            is ByteArray -> mmkv.encode(key, value)
            else -> mmkv.encode(key, value?.toString() ?: "")
        }
    }

    fun putStringSet(key: String, value: Set<String>) {
        mmkv.encode(key, value)
    }

    fun getStringSet(key: String): Set<String> {
        return mmkv.decodeStringSet(key, emptySet()) ?: emptySet()
    }

    fun getString(key: String, defaultValue: String = ""): String? =
        mmkv.decodeString(key, defaultValue)

    fun getInt(key: String, defaultValue: Int = 0): Int =
        mmkv.decodeInt(key, defaultValue)

    fun getLong(key: String, defaultValue: Long = 0L): Long =
        mmkv.decodeLong(key, defaultValue)

    fun getFloat(key: String, defaultValue: Float = 0f): Float =
        mmkv.decodeFloat(key, defaultValue)

    fun getDouble(key: String, defaultValue: Double = 0.0): Double =
        mmkv.decodeDouble(key, defaultValue)

    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean =
        mmkv.decodeBool(key, defaultValue)

    fun getBytes(key: String, defaultValue: ByteArray? = null): ByteArray? =
        mmkv.decodeBytes(key, defaultValue)

    // ==================== Parcelable 对象 ====================
    fun <T : android.os.Parcelable> putParcelable(key: String, value: T) {
        mmkv.encode(key, value)
    }

    fun <T : android.os.Parcelable> getParcelable(key: String, clazz: Class<T>): T? {
        return mmkv.decodeParcelable(key, clazz)
    }

    fun remove(key: String) {
        mmkv.removeValueForKey(key)
    }

    fun remove(vararg keys: String) {
        mmkv.removeValuesForKeys(keys)
    }

    fun clear() {
        mmkv.clearAll()
    }

    fun contains(key: String): Boolean = mmkv.containsKey(key)
}