package uz.alien.task

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import uz.alien.task.databinding.ActivityPostBinding
import uz.alien.task.post.AdapterPost
import uz.alien.task.post.Post
import uz.alien.task.post.PostRetrofit

class ActivityPost : AppCompatActivity() {

    lateinit var binding: ActivityPostBinding
    var id = 2
    val adapterPost = AdapterPost()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        instance = this

        binding.rvPosts.layoutManager = LinearLayoutManager(this)
        binding.rvPosts.adapter = adapterPost

        binding.bAddPost.setOnClickListener {
            PostRetrofit.create(Post(0, "Salom", "This is my first code from python", 1001))
        }

//        PostVolley.getAll()
    }

    companion object {
        lateinit var instance: ActivityPost
    }
}