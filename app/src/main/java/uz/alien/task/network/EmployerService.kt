package uz.alien.task.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import uz.alien.task.Employer

interface EmployerService {

    @GET("employees")
    fun getAll(): Call<DummyArray>

    @GET("employee/{id}")
    fun get(@Path("id") id: Int): Call<Dummy>

    @POST("create")
    fun post(@Body employer: Employer): Call<Dummy>

    @PUT("update/{id}")
    fun put(@Path("id") id: Int, @Body employer: Employer): Call<Dummy>

    @DELETE("delete/{id}")
    fun delete(@Path("id") id: Int): Call<Dummy>
}