package uz.alien.task.post

import android.util.Log
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import uz.alien.task.ActivityPost

object PostRetrofit {

    private val TAG = PostRetrofit::class.java.simpleName

    data class PostImport (
        @SerializedName("id")
        val id: Int = 0,

        @SerializedName("title")
        val title: String? = null,

        @SerializedName("body")
        val body: String? = null,

        @SerializedName("userId")
        val userId: Int = 0
    )

    interface PostService {

        @Headers("Content-type:application/json")

        @GET("posts")
        fun getAll(): Call<ArrayList<PostImport>>

        @GET("posts/{id}")
        fun get(@Path("id") id: Int): Call<PostImport>

        @POST("posts")
        fun post(@Body post: Post): Call<PostImport>

        @PUT("posts/{id}")
        fun put(@Path("id") id: Int, @Body post: Post): Call<PostImport>

        @DELETE("posts/{id}")
        fun delete(@Path("id") id: Int): Call<PostImport>
    }

    private val service = Retrofit
        .Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(PostService::class.java)

    fun getAll() {
        service.getAll().enqueue(object : Callback<ArrayList<PostImport>> {
            override fun onResponse(call: Call<ArrayList<PostImport>>, response: Response<ArrayList<PostImport>>) {
                Log.d(TAG, response.toString())
                if (response.body() != null) {
                    response.body()!!.forEach {
                        Log.d(TAG, it.toString())
                        ActivityPost.instance.adapterPost.add(Post(it.id, it.title.toString(), it.body.toString(), it.userId))
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<PostImport>>, t: Throwable) {
                Log.d(TAG, t.message.toString())
            }
        })
    }

    fun get(id: Int) {
        service.get(id).enqueue(object : Callback<PostImport> {
            override fun onResponse(call: Call<PostImport>, response: Response<PostImport>) {
                Log.d(TAG, response.toString())
                if (response.body() != null) {
                    Log.d(TAG, response.body().toString())
                }
            }

            override fun onFailure(call: Call<PostImport>, t: Throwable) {
                Log.d(TAG, t.message.toString())
            }
        })
    }

    fun create(post: Post) {
        service.post(post).enqueue(object : Callback<PostImport> {
            override fun onResponse(call: Call<PostImport>, response: Response<PostImport>) {
                Log.d(TAG, response.toString())
                response.body()?.let {

                    val post = Post(
                        it.id,
                        it.body.toString(),
                        it.title.toString(),
                        it.userId
                    )

                    ActivityPost.instance.adapterPost.add(post)
                    Log.d(TAG, response.body().toString())
                }
            }

            override fun onFailure(call: Call<PostImport>, t: Throwable) {
                Log.d(TAG, t.message.toString())
            }
        })
    }

    fun update(id: Int, post: Post) {
        service.put(id, post).enqueue(object : Callback<PostImport> {
            override fun onResponse(call: Call<PostImport>, response: Response<PostImport>) {
                Log.d(TAG, response.toString())
                if (response.body() != null) {
                    Log.d(TAG, response.body().toString())
                } else {
                    Log.d(TAG, response.body().toString())
                }
            }

            override fun onFailure(call: Call<PostImport>, t: Throwable) {
                Log.d(TAG, t.message.toString())
            }
        })
    }

    fun delete(post: Post, position: Int) {
        service.delete(post.id).enqueue(object : Callback<PostImport> {
            override fun onResponse(call: Call<PostImport>, response: Response<PostImport>) {
                Log.d(TAG, response.toString())
                if (response.body() != null) {
                    Log.d(TAG, response.body().toString())
                    ActivityPost.instance.adapterPost.delete(position)
                } else {
                    Log.d(TAG, response.body().toString())
                }
            }

            override fun onFailure(call: Call<PostImport>, t: Throwable) {
                Log.d(TAG, t.message.toString())
            }
        })
    }
}