package uz.alien.task

import android.app.Application
import android.content.SharedPreferences
import android.os.Handler

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        preferences = getSharedPreferences("Settings", MODE_PRIVATE)
        handler = Handler(mainLooper)
    }

    companion object {

        lateinit var preferences: SharedPreferences
        lateinit var handler: Handler
        lateinit var home: MainActivity

        fun saveValue(name: String, value: Any) {
            when (value) {
                is Int -> preferences.edit().putInt(name, value).apply()
                is Long -> preferences.edit().putLong(name, value).apply()
                is Float -> preferences.edit().putFloat(name, value).apply()
                is String -> preferences.edit().putString(name, value).apply()
                is Boolean -> preferences.edit().putBoolean(name, value).apply()
            }
        }

        fun getInt(name: String, default: Int = 0) = preferences.getInt(name, default)
        fun getLong(name: String, default: Long = 0L) = preferences.getLong(name, default)
        fun getFloat(name: String, default: Float = 0.0f) = preferences.getFloat(name, default)
        fun getString(name: String, default: String = "") = preferences.getString(name, default)
        fun getBoolean(name: String, default: Boolean = false) = preferences.getBoolean(name, default)
    }
}