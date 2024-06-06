package uz.alien.task.employer

import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import org.json.JSONException
import org.json.JSONObject
import uz.alien.task.App
import uz.alien.task.ActivityPost

object EmployerVolley {

    private const val SERVER = "https://dummy.restapiexample.com/api/v1/"
    private const val API_GET_ALL = SERVER + "employees"
    private const val API_GET = SERVER + "employee/"
    private const val API_CREATE = SERVER + "create"
    private const val API_UPDATE = SERVER + "update/"
    private const val API_DELETE = SERVER + "delete/"

    fun getAll() {
        App.addToRequestQueue(object : StringRequest(
            Method.GET, API_GET_ALL,
            Response.Listener {
                Log.d("VolleyHttp", it.toString())
            },
            Response.ErrorListener {
                Log.d("VolleyHttp", it.message.toString())
            }
        ) { override fun getParams() = hashMapOf<String, String>() })
    }

    fun get(id: Int) {
        App.addToRequestQueue(object : StringRequest(Method.GET, "$API_GET$id",
            Response.Listener {
                Log.d("VolleyHttp", it.toString())
            },
            Response.ErrorListener {
                Log.d("VolleyHttp", it.message.toString())
            }
        ) { override fun getParams() = hashMapOf<String, String>() })
    }

    fun create(employer: Employer) {
        App.addToRequestQueue(object : StringRequest(Method.POST, API_CREATE,
            Response.Listener {
                it?.let {
                    try {
                        val jsonObj = JSONObject(it)
                        val dataObj: JSONObject = jsonObj.getJSONObject("data")
                        ActivityPost.instance.id = dataObj.getInt("id")
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
                Log.d("VolleyHttp", it.toString())
            },
            Response.ErrorListener {
                Log.d("VolleyHttp", it.message.toString())
            }
        ) {
            override fun getHeaders() = HashMap<String, String>()
            override fun getBody() = JSONObject(mapOf<String, Any>("name" to employer.name, "salary" to employer.salary, "age" to employer.age)).toString().toByteArray()
        })
    }

    fun update(id: Int, employer: Employer) {
        App.addToRequestQueue(object : StringRequest(Method.PUT, "$API_UPDATE$id",
            Response.Listener {
                Log.d("VolleyHttp", it.toString())
            },
            Response.ErrorListener {
                Log.d("VolleyHttp", it.message.toString())
            }
        ) {
            override fun getHeaders() = HashMap<String, String>()
            override fun getBody() = JSONObject(mapOf<String, Any>("id" to employer.id, "name" to employer.name, "salary" to employer.salary, "age" to employer.age)).toString().toByteArray()
        })
    }

    fun delete(id: Int) {
        App.addToRequestQueue(object : StringRequest(Method.DELETE, "$API_DELETE$id",
            Response.Listener {
                Log.d("VolleyHttp", it.toString())
            },
            Response.ErrorListener {
                Log.d("VolleyHttp", it.message.toString())
            }
        ) {})
    }
}