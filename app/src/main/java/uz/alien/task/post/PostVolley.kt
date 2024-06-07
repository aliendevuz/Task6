package uz.alien.task.post

import android.util.Log
import android.view.View
import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import org.json.JSONException
import org.json.JSONObject
import uz.alien.task.App
import uz.alien.task.activity.ActivityPost

object PostVolley {

    private val TAG = PostVolley::class.java.simpleName

    private const val API_GET_ALL = "https://jsonplaceholder.typicode.com/posts"
    private const val API_GET = "https://jsonplaceholder.typicode.com/posts/"
    private const val API_CREATE = "https://jsonplaceholder.typicode.com/posts"
    private const val API_UPDATE = "https://jsonplaceholder.typicode.com/posts/"
    private const val API_DELETE = "https://jsonplaceholder.typicode.com/posts/"

    fun getAll() {
        ActivityPost.instance.binding.pbLoading.visibility = View.VISIBLE
        App.addToRequestQueue(object : StringRequest(
            Method.GET, API_GET_ALL,
            Response.Listener {
                Log.d(TAG, it.toString())
                App.gson.fromJson(it, Array<Post>::class.java).forEach { it1 ->
                    ActivityPost.instance.adapterPost.add(it1)
                }
                ActivityPost.instance.binding.pbLoading.visibility = View.GONE
            },
            Response.ErrorListener {
                Log.d(TAG, it.message.toString())
                ActivityPost.instance.binding.pbLoading.visibility = View.GONE
                ActivityPost.showSnackbar("Loading failed!")
            }
        ) {
            override fun getParams() = hashMapOf<String, String>()
        })
    }

    fun get(id: Int) {
        ActivityPost.instance.binding.pbLoading.visibility = View.VISIBLE
        App.addToRequestQueue(object : StringRequest(
            Method.GET, "$API_GET$id",
            Response.Listener {
                Log.d(TAG, it.toString())
                App.gson.fromJson(it, Post::class.java)
                ActivityPost.instance.binding.pbLoading.visibility = View.GONE
            },
            Response.ErrorListener {
                Log.d(TAG, it.message.toString())
                ActivityPost.instance.binding.pbLoading.visibility = View.GONE
                ActivityPost.showSnackbar("Loading failed!")
            }
        ) {
            override fun getParams() = hashMapOf<String, String>()
        })
    }

    fun create(post: Post) {
        ActivityPost.instance.binding.pbLoading.visibility = View.VISIBLE
        App.addToRequestQueue(object : StringRequest(
            Method.POST, API_CREATE,
            Response.Listener {
                Log.d(TAG, it.toString())
                it?.let {
                    val post = App.gson.fromJson(it, Post::class.java)
                    ActivityPost.instance.id = post.id
                    ActivityPost.showSnackbar("Post created!")
                    ActivityPost.instance.adapterPost.add(post)
                }
                ActivityPost.instance.binding.pbLoading.visibility = View.GONE
            },
            Response.ErrorListener {
                Log.d(TAG, it.message.toString())
                ActivityPost.showSnackbar("Creating failed!")
                ActivityPost.instance.binding.pbLoading.visibility = View.GONE
            }
        ) {
            override fun getHeaders() = hashMapOf("Content-type" to "application/json; charset=UTF-8")
            override fun getBody() = JSONObject(mapOf<String, Any>("title" to post.title, "body" to post.body, "userId" to post.userId)).toString().toByteArray()
        })
    }

    fun update(position: Int, post: Post) {
        ActivityPost.instance.binding.pbLoading.visibility = View.VISIBLE
        App.addToRequestQueue(object : StringRequest(
            Method.PUT, "$API_UPDATE${post.id}",
            Response.Listener {
                Log.d(TAG, it.toString())
                it?.let {
                    val post = App.gson.fromJson(it, Post::class.java)
                    ActivityPost.instance.adapterPost.update(post, position)
                    ActivityPost.showSnackbar("Post updated!")
                }
                ActivityPost.instance.binding.pbLoading.visibility = View.GONE
            },
            Response.ErrorListener {
                Log.d(TAG, it.message.toString())
                ActivityPost.showSnackbar("Updating failed!")
                ActivityPost.instance.binding.pbLoading.visibility = View.GONE
            }
        ) {
            override fun getHeaders() = hashMapOf("Content-type" to "application/json; charset=UTF-8")
            override fun getBody() = JSONObject(mapOf<String, Any>("id" to post.id, "title" to post.title, "body" to post.body, "userId" to post.userId)).toString().toByteArray()
        })
    }

    fun delete(post: Post, position: Int) {
        ActivityPost.instance.binding.pbLoading.visibility = View.VISIBLE
        App.addToRequestQueue(object : StringRequest(
            Method.DELETE, "$API_DELETE$post",
            Response.Listener {
                Log.d(TAG, it.toString())
                if (it == "{}") {
                    ActivityPost.instance.adapterPost.delete(position)
                    ActivityPost.showSnackbar("Post deleted!")
                }
                ActivityPost.instance.binding.pbLoading.visibility = View.GONE
            },
            Response.ErrorListener {
                Log.d(TAG, it.message.toString())
                ActivityPost.instance.adapterPost.update(post, position)
                ActivityPost.showSnackbar("Deleting failed!")
                ActivityPost.instance.binding.pbLoading.visibility = View.GONE
            }
        ) {})
    }
}