package uz.alien.task.network

import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import org.json.JSONObject
import uz.alien.task.App
import uz.alien.task.Employer

class VolleyHttp {

    companion object {

        interface VolleyHandler {
            fun onSuccess(response: String?)
            fun onError(error: String?)
        }

        private const val SERVER_DEVELOPMENT = "https://dummy.restapiexample.com/api/v1/"
        private const val SERVER_PRODUCTION = "https://dummy.restapiexample.com/api/v1/"
        const val IS_TESTER = true
        const val TAG = "VolleyHttp"

        const val API_LIST_POST = "employees"
        const val API_SINGLE_POST = "employee/"
        const val API_CREATE_POST = "create"
        const val API_UPDATE_POST = "update/"
        const val API_DELETE_POST = "delete/"

        fun server(url: String): String {
            return if (IS_TESTER) SERVER_DEVELOPMENT + url
            else SERVER_DEVELOPMENT + url
        }

        fun headers(): HashMap<String, String> {
            //            headers["Content-type"] = "application/json; charset=UTF-8"
            return HashMap()
        }

        fun getAll(api: String, params: HashMap<String, String>, volleyHandler: VolleyHandler) {
            val stringRequest = object : StringRequest(
                Method.GET, server(api),
                Response.Listener { response ->
                    if (IS_TESTER) Log.d(TAG, response)
                    volleyHandler.onSuccess(response)
                },
                Response.ErrorListener { error ->
                    if (IS_TESTER) Log.d(TAG, error.toString())
                    volleyHandler.onError(error.toString())
                }
            ) {
                override fun getParams(): MutableMap<String, String> {
                    return params
                }
            }
            Log.d(TAG, server(api))
            App.addToRequestQueue(stringRequest)
        }

        fun get(api: String, id: Int, params: HashMap<String, String>, volleyHandler: VolleyHandler) {
            val stringRequest = object : StringRequest(
                Method.GET, server(api) + id.toString(),
                Response.Listener { response ->
                    if (IS_TESTER) Log.d(TAG, response)
                    volleyHandler.onSuccess(response)
                },
                Response.ErrorListener { error ->
                    if (IS_TESTER) Log.d(TAG, error.toString())
                    volleyHandler.onError(error.toString())
                }
            ) {
                override fun getParams(): MutableMap<String, String> {
                    return params
                }
            }
            Log.d(TAG, server(api) + id.toString())
            App.addToRequestQueue(stringRequest)
        }

        fun post(api: String, body: HashMap<String, Any>, volleyHandler: VolleyHandler) {
            val stringRequest = object : StringRequest(
                Method.POST, server(api),
                Response.Listener { response ->
                    if (IS_TESTER) Log.d(TAG, response)
                    volleyHandler.onSuccess(response)
                },
                Response.ErrorListener { error ->
                    if (IS_TESTER) Log.d(TAG, error.toString())
                    volleyHandler.onError(error.toString())
                }
            ) {
                override fun getHeaders(): MutableMap<String, String> {
                    return headers()
                }

                override fun getBody(): ByteArray {
                    return JSONObject(body as Map<*, *>).toString().toByteArray()
                }
            }
            Log.d(TAG, server(api))
            App.addToRequestQueue(stringRequest)
        }

        fun put(api: String, id: Int, body: HashMap<String, Any>, volleyHandler: VolleyHandler) {
            val stringRequest = object : StringRequest(
                Method.PUT, server(api) + id.toString(),
                Response.Listener { response ->
                    if (IS_TESTER) Log.d(TAG, response)
                    volleyHandler.onSuccess(response)
                },
                Response.ErrorListener { error ->
                    if (IS_TESTER) Log.d(TAG, error.toString())
                    volleyHandler.onError(error.toString())
                }
            ) {
                override fun getHeaders(): MutableMap<String, String> {
                    return headers()
                }

                override fun getBody(): ByteArray {
                    return JSONObject(body as Map<*, *>).toString().toByteArray()
                }
            }
            Log.d(TAG, server(api) + id.toString())
            App.addToRequestQueue(stringRequest)
        }

        fun delete(url: String, id: Int, volleyHandler: VolleyHandler) {
            val stringRequest = object : StringRequest(
                Method.DELETE, server(url) + id.toString(),
                Response.Listener { response ->
                    if (IS_TESTER) Log.d(TAG, response)
                    volleyHandler.onSuccess(response)
                },
                Response.ErrorListener { error ->
                    if (IS_TESTER) Log.d(TAG, error.toString())
                    volleyHandler.onError(error.toString())
                }
            ) {}
            Log.d(TAG, server(url) + id.toString())
            App.addToRequestQueue(stringRequest)
        }

        fun pramsEmpty() = HashMap<String, String>()

        fun paramsCreate(employer: Employer): HashMap<String, Any> {
            val params = HashMap<String, Any>()
            params["name"] = employer.name
            params["salary"] = employer.salary
            params["age"] = employer.age
            return params
        }

        fun paramsUpdate(employer: Employer): HashMap<String, Any> {
            val params = HashMap<String, Any>()
            params["id"] = employer.id
            params["name"] = employer.name
            params["salary"] = employer.salary
            params["age"] = employer.age
            return params
        }
    }
}