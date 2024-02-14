package uz.alien.task.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHttp {

    private const val SERVER_DEVELOPMENT = "https://dummy.restapiexample.com/api/v1/"
//    private const val SERVER_DEVELOPMENT = "https://jsonplaceholder.typicode.com/"
    private const val SERVER_PRODUCTION = "https://dummy.restapiexample.com/api/v1/"
//    private const val SERVER_PRODUCTION = "https://jsonplaceholder.typicode.com/"
    const val IS_TESTER = true
    const val TAG = "RetrofitHttp"

    val retrofit = Retrofit.Builder().baseUrl(server()).addConverterFactory(GsonConverterFactory.create()).build()

    fun server() = if (IS_TESTER) SERVER_DEVELOPMENT else SERVER_PRODUCTION

    val employerService: EmployerService = retrofit.create(EmployerService::class.java)
}