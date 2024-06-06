package uz.alien.task

import android.app.Application
import android.content.SharedPreferences
import android.os.Handler
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.google.gson.Gson

class App : Application() {

    private val _requestQueue: RequestQueue? = null
        get() { return field ?: Volley.newRequestQueue(baseContext) }

    override fun onCreate() {
        super.onCreate()
        instance = this
        handler = Handler(mainLooper)
        requestQueue = _requestQueue!!
    }

    companion object {

        lateinit var instance: App
        lateinit var handler: Handler
        val gson = Gson()

        lateinit var requestQueue: RequestQueue

        fun <T> addToRequestQueue(request: Request<T>) {
            Log.d("addToRequestQueue", request.url)
            requestQueue.add(request)
        }
    }
}