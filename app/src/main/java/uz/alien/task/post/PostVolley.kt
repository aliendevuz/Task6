package uz.alien.task.post

import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import org.json.JSONException
import org.json.JSONObject
import uz.alien.task.App
import uz.alien.task.ActivityPost

object PostVolley {

    private val TAG = PostVolley::class.java.simpleName

    private const val SERVER = "https://jsonplaceholder.typicode.com/"
    private const val API_GET_ALL = SERVER + "posts"
    private const val API_GET = SERVER + "posts/"
    private const val API_CREATE = SERVER + "posts"
    private const val API_UPDATE = SERVER + "posts/"
    private const val API_DELETE = SERVER + "posts/"

    fun getAll() {
        App.addToRequestQueue(object : StringRequest(
            Method.GET, API_GET_ALL,
            Response.Listener {
                Log.d(TAG, it.toString())
                App.gson.fromJson(it, Array<Post>::class.java).forEach { it1 ->
                    ActivityPost.instance.adapterPost.add(it1)
                }
            },
            Response.ErrorListener {
                Log.d(TAG, it.message.toString())
            }
        ) { override fun getParams() = hashMapOf<String, String>() })
    }

    fun get(id: Int) {
        App.addToRequestQueue(object : StringRequest(
            Method.GET, "$API_GET$id",
            Response.Listener {
                Log.d(TAG, it.toString())
            },
            Response.ErrorListener {
                Log.d(TAG, it.message.toString())
            }
        ) { override fun getParams() = hashMapOf<String, String>() })
    }

    fun create(post: Post) {
        App.addToRequestQueue(object : StringRequest(
            Method.POST, API_CREATE,
            Response.Listener {
                it?.let {
                    try {
                        val id = JSONObject(it).getInt("id")
                        Log.d(TAG, "Current ID = $id")
                        ActivityPost.instance.id = id
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
                Log.d(TAG, it.toString())
            },
            Response.ErrorListener {
                Log.d(TAG, it.message.toString())
            }
        ) {
            override fun getHeaders() = hashMapOf("Content-type" to "application/json; charset=UTF-8")
            override fun getBody() = JSONObject(mapOf<String, Any>("title" to post.title, "body" to post.body, "userId" to post.userId)).toString().toByteArray()
        })
    }

    fun update(id: Int, post: Post) {
        App.addToRequestQueue(object : StringRequest(
            Method.PUT, "$API_UPDATE$id",
            Response.Listener {
                Log.d(TAG, it.toString())
            },
            Response.ErrorListener {
                Log.d(TAG, it.message.toString())
            }
        ) {
            override fun getHeaders() = hashMapOf("Content-type" to "application/json; charset=UTF-8")
            override fun getBody() = JSONObject(mapOf<String, Any>("id" to post.id, "title" to post.title, "body" to post.body, "userId" to post.userId)).toString().toByteArray()
        })
    }

    fun delete(post: Post, position: Int) {
        App.addToRequestQueue(object : StringRequest(
            Method.DELETE, "$API_DELETE$post",
            Response.Listener {
                Log.d(TAG, it.toString())
                ActivityPost.instance.adapterPost.delete(position)
            },
            Response.ErrorListener {
                Log.d(TAG, it.message.toString())
            }
        ) {})
    }
}