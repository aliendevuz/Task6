package uz.alien.task

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONException
import org.json.JSONObject
import uz.alien.task.databinding.ActivityMainBinding
import uz.alien.task.network.VolleyHttp


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    var id = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bGetAllVolley.setOnClickListener {
            apiVolleyList()
        }

        binding.bGetVolley.setOnClickListener {
            apiVolleyGet(1)
        }

        binding.bPostVolley.setOnClickListener {
            apiVolleyPost(Employer(0, "Khalilov Ibrohim", "500", "20"))
        }

        binding.bPutVolley.setOnClickListener {
            apiVolleyPut(id, Employer(id, "Khalilov Ibrohim", "1200", "23"))
        }

        binding.bDeleteVolley.setOnClickListener {
            apiVolleyDelete(2)
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
}