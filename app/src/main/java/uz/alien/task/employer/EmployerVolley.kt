package uz.alien.task.employer

import android.util.Log
import android.view.View
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.gson.annotations.SerializedName
import org.json.JSONObject
import uz.alien.task.App
import uz.alien.task.activity.ActivityEmployer
import uz.alien.task.activity.ActivityPost
import uz.alien.task.post.Post
import uz.alien.task.post.PostVolley

object EmployerVolley {

    data class EmployerImport(
        val id: Int = 0,
        val employee_name: String? = null,
        val employee_salary: Int = 0,
        val employee_age: Int = 0,
    )

    data class EmployerImport2(
        val id: Int = 0,
        val name: String? = null,
        val salary: Int = 0,
        val age: Int = 0,
    )

    data class ResponseEmployer(
        @SerializedName("status")
        val status: String? = null,

        @SerializedName("data")
        val data: EmployerImport? = null,

        @SerializedName("message")
        val message: String? = null
    ) {
        data class EmployerImport(
            val id: Int = 0,
            val name: String? = null,
            val salary: Int = 0,
            val age: Int = 0,
        ) {
            override fun toString(): String {
                return "EI(id=$id, name=$name, salary=$salary, age=$age)"
            }
        }

        override fun toString(): String {
            return "RE(status=$status, data=$data, message=$message)"
        }
    }

    private val TAG = EmployerVolley::class.java.simpleName

    private const val API_GET_ALL = "https://dummy.restapiexample.com/api/v1/employees"
    private const val API_GET = "https://dummy.restapiexample.com/api/v1/employee/"
    private const val API_CREATE = "https://dummy.restapiexample.com/api/v1/create"
    private const val API_UPDATE = "https://dummy.restapiexample.com/api/v1/update/"
    private const val API_DELETE = "https://dummy.restapiexample.com/api/v1/delete/"

    fun getAll() {
        ActivityEmployer.instance.binding.pbLoading.visibility = View.VISIBLE
        App.addToRequestQueue(object : StringRequest(
            Method.GET, API_GET_ALL,
            Response.Listener {
                Log.d(TAG, it.toString())
                Log.d(TAG, JSONObject(it).get("data").toString())
                JSONObject(it).get("data")
                App.gson.fromJson(JSONObject(it).get("data").toString(), Array<EmployerImport>::class.java).forEach { it1 ->
                    val employer = Employer(it1.id, it1.employee_name.toString(), it1.employee_salary.toString(),
                        it1.employee_age.toString()
                    )
                    ActivityEmployer.instance.adapterEmployee.add(employer)
                }
                ActivityEmployer.instance.binding.pbLoading.visibility = View.GONE
            },
            Response.ErrorListener {
                Log.d(TAG, it.message.toString())
                ActivityEmployer.instance.binding.pbLoading.visibility = View.GONE
                ActivityEmployer.showSnackbar("Loading failed!")
            }
        ) {
            override fun getParams() = hashMapOf<String, String>()
        })
    }

    fun get(id: Int) {
        ActivityEmployer.instance.binding.pbLoading.visibility = View.VISIBLE
        App.addToRequestQueue(object : StringRequest(
            Method.GET, "$API_GET$id",
            Response.Listener {
                Log.d(TAG, it.toString())
                App.gson.fromJson(JSONObject(it).get("data").toString(), EmployerImport::class.java)
                ActivityEmployer.instance.binding.pbLoading.visibility = View.GONE
            },
            Response.ErrorListener {
                Log.d(TAG, it.message.toString())
                ActivityEmployer.instance.binding.pbLoading.visibility = View.GONE
                ActivityEmployer.showSnackbar("Loading failed!")
            }
        ) {
            override fun getParams() = hashMapOf<String, String>()
        })
    }

    fun create(employer: Employer) {
        ActivityEmployer.instance.binding.pbLoading.visibility = View.VISIBLE
        App.addToRequestQueue(object : StringRequest(
            Method.POST, API_CREATE,
            Response.Listener {
                Log.d(TAG, it.toString())
                it?.let {
                    App.gson.fromJson(it, ResponseEmployer::class.java)?.let {
                        if (it.status == "success") {
                            ActivityEmployer.showSnackbar("Employer created!")
                            ActivityEmployer.instance.adapterEmployee.add(employer)
                        }
                    }
                }
                ActivityEmployer.instance.binding.pbLoading.visibility = View.GONE
            },
            Response.ErrorListener {
                Log.d(TAG, it.message.toString())
                ActivityEmployer.instance.binding.pbLoading.visibility = View.GONE
                ActivityEmployer.showSnackbar("Creating failed!")
            }
        ) {
            override fun getHeaders() = HashMap<String, String>()
            override fun getBody() = JSONObject(mapOf<String, Any>("name" to employer.name, "salary" to employer.salary, "age" to employer.age)).toString().toByteArray()
        })
    }

    fun update(position: Int, employer: Employer) {
        ActivityEmployer.instance.binding.pbLoading.visibility = View.VISIBLE
        App.addToRequestQueue(object : StringRequest(
            Method.PUT, "$API_UPDATE${employer.id}",
            Response.Listener {
                Log.d(TAG, it)
                it?.let {

                    App.gson.fromJson(it, ResponseEmployer::class.java)?.let {
                        if (it.status == "success") {

                            ActivityEmployer.instance.adapterEmployee.update(employer, position)
                            ActivityEmployer.showSnackbar("Employer updated!")
                        }
                    }
                }
                ActivityEmployer.instance.binding.pbLoading.visibility = View.GONE
            },
            Response.ErrorListener {
                Log.d(TAG, it.message.toString())
                ActivityEmployer.instance.binding.pbLoading.visibility = View.GONE
                ActivityEmployer.showSnackbar("Updating failed!")
            }
        ) {
            override fun getHeaders() = HashMap<String, String>()
            override fun getBody() = JSONObject(mapOf<String, Any>("name" to employer.name, "salary" to employer.salary, "age" to employer.age)).toString().toByteArray()
        })
    }

    fun delete(employer: Employer, position: Int) {
        ActivityEmployer.instance.binding.pbLoading.visibility = View.VISIBLE
        App.addToRequestQueue(object : StringRequest(
            Method.DELETE, "$API_DELETE${employer.id}",
            Response.Listener {
                Log.d(TAG, it.toString())
                if (JSONObject(it).get("status") == "success") {
                    ActivityEmployer.instance.adapterEmployee.delete(position)
                    ActivityEmployer.showSnackbar("Employer deleted!")
                }
                ActivityEmployer.instance.binding.pbLoading.visibility = View.GONE
            },
            Response.ErrorListener {
                Log.d(TAG, it.message.toString())
                ActivityEmployer.instance.binding.pbLoading.visibility = View.GONE
                ActivityEmployer.showSnackbar("Deleting failed!")
                ActivityEmployer.instance.adapterEmployee.notifyItemChanged(position)
            }
        ) {})
    }
}