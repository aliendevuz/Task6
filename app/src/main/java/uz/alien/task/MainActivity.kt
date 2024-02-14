package uz.alien.task

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.alien.task.databinding.ActivityMainBinding
import uz.alien.task.network.Dummy
import uz.alien.task.network.DummyArray
import uz.alien.task.network.EmployerResp
import uz.alien.task.network.RetrofitHttp
import uz.alien.task.network.VolleyHttp

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    var id = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bGetAllVolley.setOnClickListener {
//            apiVolleyList()
            apiRetrofitGetAll()
        }

        binding.bGetVolley.setOnClickListener {
//            apiVolleyGet(1)
            apiRetrofitGet(1)
        }

        binding.bPostVolley.setOnClickListener {
//            apiVolleyPost(Employer(0, "Khalilov Ibrohim", "500", "20"))
            apiRetrofitPost(Employer(0, "Khalilov Ibrohim", "500", "20"))
        }

        binding.bPutVolley.setOnClickListener {
//            apiVolleyPut(id, Employer(id, "Khalilov Ibrohim", "1200", "23"))
            apiRetrofitPut(id, Employer(id, "Khalilov Ibrohim", "1200", "23"))
        }

        binding.bDeleteVolley.setOnClickListener {
//            apiVolleyDelete(2)
            apiRetrofitDelete(2)
        }
    }

    fun apiVolleyList() {
        VolleyHttp.getAll(VolleyHttp.API_LIST_POST, VolleyHttp.pramsEmpty(), object : VolleyHttp.Companion.VolleyHandler {
            override fun onSuccess(response: String?) {
                binding.tvStatus.text = "Get All: Success!"
            }
            override fun onError(error: String?) {
                binding.tvStatus.text = "Get All: Failed!"
            }
        })
    }

    fun apiVolleyGet(id: Int) {
        VolleyHttp.get(VolleyHttp.API_SINGLE_POST, id, VolleyHttp.pramsEmpty(), object : VolleyHttp.Companion.VolleyHandler {
            override fun onSuccess(response: String?) {
                binding.tvStatus.text = "Get: Success!"
            }
            override fun onError(error: String?) {
                binding.tvStatus.text = "Get: Failed!"
            }
        })
    }

    fun apiVolleyPost(employer: Employer) {
        VolleyHttp.post(VolleyHttp.API_CREATE_POST, VolleyHttp.paramsCreate(employer), object : VolleyHttp.Companion.VolleyHandler {
            override fun onSuccess(response: String?) {
                response?.let {
                    try {
                        val jsonObj = JSONObject(it)
                        val dataObj: JSONObject = jsonObj.getJSONObject("data")
                        id = dataObj.getInt("id")
                        binding.tvStatus.text = "Create: Success!"
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            }
            override fun onError(error: String?) {
                binding.tvStatus.text = "Create: Failed!"
            }
        })
    }

    fun apiVolleyPut(id: Int, employer: Employer) {
        VolleyHttp.put(VolleyHttp.API_UPDATE_POST, id, VolleyHttp.paramsUpdate(employer), object : VolleyHttp.Companion.VolleyHandler {
            override fun onSuccess(response: String?) {
                binding.tvStatus.text = "Update: Success!"
            }
            override fun onError(error: String?) {
                binding.tvStatus.text = "Update: Failed!"
            }
        })
    }

    fun apiVolleyDelete(id: Int) {
        VolleyHttp.delete(VolleyHttp.API_DELETE_POST, id, object : VolleyHttp.Companion.VolleyHandler {
            override fun onSuccess(response: String?) {
                binding.tvStatus.text = "Delete: Success!"
            }
            override fun onError(error: String?) {
                binding.tvStatus.text = "Delete: Failed!"
            }
        })
    }

    fun apiRetrofitGetAll() {
        RetrofitHttp.employerService.getAll().enqueue(object : Callback<DummyArray> {
            override fun onResponse(call: Call<DummyArray>, response: Response<DummyArray>) {
                if (RetrofitHttp.IS_TESTER) Log.d(RetrofitHttp.TAG, response.toString())
                if (response.body() != null) {
                    binding.tvStatus.text = "Get: Success!"
                    if (RetrofitHttp.IS_TESTER) Log.d(RetrofitHttp.TAG, response.body().toString())
                }
            }

            override fun onFailure(call: Call<DummyArray>, t: Throwable) {
                binding.tvStatus.text = "Get All: Failed!"
                if (RetrofitHttp.IS_TESTER) Log.d(RetrofitHttp.TAG, t.message.toString())
            }
        })
    }

    fun apiRetrofitGet(id: Int) {
        RetrofitHttp.employerService.get(id).enqueue(object : Callback<Dummy> {
            override fun onResponse(call: Call<Dummy>, response: Response<Dummy>) {
                if (RetrofitHttp.IS_TESTER) Log.d(RetrofitHttp.TAG, response.toString())
                if (response.body() != null) {
                    binding.tvStatus.text = "Get: Success!"
                    if (RetrofitHttp.IS_TESTER) Log.d(RetrofitHttp.TAG, response.body().toString())
                }
            }

            override fun onFailure(call: Call<Dummy>, t: Throwable) {
                binding.tvStatus.text = "Get: Failed!"
                if (RetrofitHttp.IS_TESTER) Log.d(RetrofitHttp.TAG, t.message.toString())
            }
        })
    }

    fun apiRetrofitPost(employer: Employer) {
        RetrofitHttp.employerService.post(employer).enqueue(object : Callback<Dummy> {
            override fun onResponse(call: Call<Dummy>, response: Response<Dummy>) {
                if (RetrofitHttp.IS_TESTER) Log.d(RetrofitHttp.TAG, response.toString())
                if (response.body() != null) {
                    binding.tvStatus.text = "Get: Success!"
                    if (RetrofitHttp.IS_TESTER) Log.d(RetrofitHttp.TAG, response.body().toString())
                }
            }

            override fun onFailure(call: Call<Dummy>, t: Throwable) {
                binding.tvStatus.text = "Post: Failed!"
                if (RetrofitHttp.IS_TESTER) Log.d(RetrofitHttp.TAG, t.message.toString())
            }
        })
    }

    fun apiRetrofitPut(id: Int, employer: Employer) {
        RetrofitHttp.employerService.put(id, employer).enqueue(object : Callback<Dummy> {
            override fun onResponse(call: Call<Dummy>, response: Response<Dummy>) {
                if (RetrofitHttp.IS_TESTER) Log.d(RetrofitHttp.TAG, response.toString())
                if (response.body() != null) {
                    binding.tvStatus.text = "Get: Success!"
                    if (RetrofitHttp.IS_TESTER) Log.d(RetrofitHttp.TAG, response.body().toString())
                }
            }

            override fun onFailure(call: Call<Dummy>, t: Throwable) {
                binding.tvStatus.text = "Put: Failed!"
                if (RetrofitHttp.IS_TESTER) Log.d(RetrofitHttp.TAG, t.message.toString())
            }
        })
    }

    fun apiRetrofitDelete(id: Int) {
        RetrofitHttp.employerService.delete(id).enqueue(object : Callback<Dummy> {
            override fun onResponse(call: Call<Dummy>, response: Response<Dummy>) {
                if (RetrofitHttp.IS_TESTER) Log.d(RetrofitHttp.TAG, response.toString())
                if (response.body() != null) {
                    binding.tvStatus.text = "Get: Success!"
                    if (RetrofitHttp.IS_TESTER) Log.d(RetrofitHttp.TAG, response.body().toString())
                }
            }

            override fun onFailure(call: Call<Dummy>, t: Throwable) {
                if (RetrofitHttp.IS_TESTER) Log.d(RetrofitHttp.TAG, t.message.toString())
                binding.tvStatus.text = "Delete: Failed!"
            }
        })
    }
}