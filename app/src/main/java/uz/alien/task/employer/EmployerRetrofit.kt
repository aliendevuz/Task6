package uz.alien.task.employer

import android.util.Log
import android.view.View
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import uz.alien.task.activity.ActivityEmployer

object EmployerRetrofit {

    data class ResponseId(
        @SerializedName("status")
        val status: String? = null,

        @SerializedName("data")
        val data: Int = 0,

        @SerializedName("message")
        val message: String? = null
    )

    data class ResponseModify(
        @SerializedName("status")
        val status: String? = null,

        @SerializedName("data")
        val data: EmployerImport? = null,

        @SerializedName("message")
        val message: String? = null
    ) {
        data class EmployerImport(
            @SerializedName("id")
            val id: Int = 0,

            @SerializedName("name")
            val name: String? = null,

            @SerializedName("salary")
            val salary: Int = 0,

            @SerializedName("age")
            val age: Int = 0,

            @SerializedName("profile_image")
            val profileImage: String = ""
        )
    }

    data class ResponseEmployer(
        @SerializedName("status")
        val status: String? = null,

        @SerializedName("data")
        val data: EmployerImport? = null,

        @SerializedName("message")
        val message: String? = null
    ) {
        data class EmployerImport(
            @SerializedName("id")
            val id: Int = 0,

            @SerializedName("employee_name")
            val name: String? = null,

            @SerializedName("employee_salary")
            val salary: Int = 0,

            @SerializedName("employee_age")
            val age: Int = 0,

            @SerializedName("profile_image")
            val profileImage: String = ""
        )
    }

    data class ResponseEmployerList(
        @SerializedName("status")
        val status: String? = null,

        @SerializedName("data")
        val data: ArrayList<EmployerImport> = ArrayList(),

        @SerializedName("message")
        val message: String? = null
    ) {
        data class EmployerImport(
            @SerializedName("id")
            val id: Int = 0,

            @SerializedName("employee_name")
            val name: String? = null,

            @SerializedName("employee_salary")
            val salary: Int = 0,

            @SerializedName("employee_age")
            val age: Int = 0,

            @SerializedName("profile_image")
            val profileImage: String = ""
        )
    }

    interface EmployerService {

        @GET("employees")
        fun getAll(): Call<ResponseEmployerList>

        @GET("employee/{id}")
        fun get(@Path("id") id: Int): Call<ResponseEmployer>

        @POST("create")
        fun post(@Body employer: Employer): Call<ResponseModify>

        @PUT("update/{id}")
        fun put(@Path("id") id: Int, @Body employer: Employer): Call<ResponseModify>

        @DELETE("delete/{id}")
        fun delete(@Path("id") id: Int): Call<ResponseId>
    }

    private val service = Retrofit
        .Builder()
        .baseUrl("https://dummy.restapiexample.com/api/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(EmployerService::class.java)

    fun getAll() {

        ActivityEmployer.instance.binding.pbLoading.visibility = View.VISIBLE

        service.getAll().enqueue(object : Callback<ResponseEmployerList> {

            override fun onResponse(call: Call<ResponseEmployerList>, response: Response<ResponseEmployerList>) {
                Log.d("Retrofit", response.toString())
                Log.d("Retrofit", response.body().toString())

                if (response.code() == 200) {
                    response.body()?.let {
                        ActivityEmployer.instance.adapterEmployee.clear()
                        val employerList = it.data
                        for (e in employerList) {
                            val employer = Employer(
                                e.id,
                                e.name.toString(),
                                e.salary.toString(),
                                e.age.toString()
                            )
                            ActivityEmployer.instance.adapterEmployee.add(employer)
                        }
                    }
                } else {
                    ActivityEmployer.instance.showSnackbar("Failed to load!")
                    ActivityEmployer.instance.showSnackbar("Tap to retry!")
                }
                ActivityEmployer.instance.binding.pbLoading.visibility = View.GONE
            }

            override fun onFailure(call: Call<ResponseEmployerList>, t: Throwable) {
                Log.d("Retrofit", t.message.toString())
                ActivityEmployer.instance.binding.pbLoading.visibility = View.GONE
                ActivityEmployer.instance.showSnackbar("Failed to load!")
            }
        })
    }

    fun get(id: Int) {
        ActivityEmployer.instance.binding.pbLoading.visibility = View.VISIBLE
        service.get(id).enqueue(object : Callback<ResponseEmployer> {
            override fun onResponse(call: Call<ResponseEmployer>, response: Response<ResponseEmployer>) {
                Log.d("Retrofit", response.toString())
                if (response.body() != null) {
                    Log.d("Retrofit", response.body().toString())
                } else {
                    ActivityEmployer.instance.showSnackbar("Failed to load!")
                }
                ActivityEmployer.instance.binding.pbLoading.visibility = View.GONE
            }

            override fun onFailure(call: Call<ResponseEmployer>, t: Throwable) {
                Log.d("Retrofit", t.message.toString())
                ActivityEmployer.instance.binding.pbLoading.visibility = View.GONE
                ActivityEmployer.instance.showSnackbar("Failed to load!")
            }
        })
    }

    fun create(employer: Employer) {
        ActivityEmployer.instance.binding.pbLoading.visibility = View.VISIBLE
        service.post(employer).enqueue(object : Callback<ResponseModify> {
            override fun onResponse(call: Call<ResponseModify>, response: Response<ResponseModify>) {
                Log.d("Retrofit", response.toString())
                if (response.body() != null) {
                    Log.d("Retrofit", response.body().toString())
                    ActivityEmployer.instance.adapterEmployee.add(employer)
                } else {
                    ActivityEmployer.instance.showSnackbar("Failed to create!")
                }
                ActivityEmployer.instance.binding.pbLoading.visibility = View.GONE
            }

            override fun onFailure(call: Call<ResponseModify>, t: Throwable) {
                Log.d("Retrofit", t.message.toString())
                ActivityEmployer.instance.binding.pbLoading.visibility = View.GONE
                ActivityEmployer.instance.showSnackbar("Failed to create!")
            }
        })
    }

    fun update(id: Int, employer: Employer) {
        ActivityEmployer.instance.binding.pbLoading.visibility = View.VISIBLE
        service.put(id, employer).enqueue(object : Callback<ResponseModify> {
            override fun onResponse(call: Call<ResponseModify>, response: Response<ResponseModify>) {
                Log.d("Retrofit", response.toString())
                if (response.body() != null) {
                    Log.d("Retrofit", response.body().toString())
                } else {
                    ActivityEmployer.instance.showSnackbar("Failed to update!")
                }
                ActivityEmployer.instance.binding.pbLoading.visibility = View.GONE
            }

            override fun onFailure(call: Call<ResponseModify>, t: Throwable) {
                Log.d("Retrofit", t.message.toString())
                ActivityEmployer.instance.binding.pbLoading.visibility = View.GONE
                ActivityEmployer.instance.showSnackbar("Failed to update!")
            }
        })
    }

    fun delete(employer: Employer, position: Int) {
        ActivityEmployer.instance.binding.pbLoading.visibility = View.VISIBLE
        service.delete(employer.id).enqueue(object : Callback<ResponseId> {
            override fun onResponse(call: Call<ResponseId>, response: Response<ResponseId>) {
                Log.d("Retrofit", response.toString())
                if (response.body() != null) {
                    ActivityEmployer.instance.adapterEmployee.delete(position)
                    Log.d("Retrofit", response.body().toString())
                } else {
                    ActivityEmployer.instance.showSnackbar("Failed to delete!")
                }
                ActivityEmployer.instance.binding.pbLoading.visibility = View.GONE
            }

            override fun onFailure(call: Call<ResponseId>, t: Throwable) {
                Log.d("Retrofit", t.message.toString())
                ActivityEmployer.instance.binding.pbLoading.visibility = View.GONE
                ActivityEmployer.instance.showSnackbar("Failed to delete!")
            }
        })
    }
}