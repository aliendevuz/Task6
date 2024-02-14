package uz.alien.task

import android.app.Application
import android.content.SharedPreferences
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class App : Application() {

    private val _requestQueue: RequestQueue? = null
        get() { return field ?: Volley.newRequestQueue(baseContext) }

    override fun onCreate() {
        super.onCreate()
        preferences = getSharedPreferences("Settings", MODE_PRIVATE)
        requestQueue = _requestQueue!!
    }

    companion object {

        lateinit var preferences: SharedPreferences

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


        lateinit var requestQueue: RequestQueue

        fun <T> addToRequestQueue(request: Request<T>) {
            requestQueue.add(request)
        }
    }
}