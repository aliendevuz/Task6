package uz.alien.task.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import uz.alien.task.databinding.ActivityCreatePostBinding
import uz.alien.task.post.Post

class ActivityCreatePost : AppCompatActivity() {

    lateinit var binding: ActivityCreatePostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatePostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val newId = 20
        val userId = 45

        binding.bCreatePost.setOnClickListener {

            val intent = Intent(this, ActivityPost::class.java)

            val post = Post(newId, binding.etTitle.text.toString(), binding.etCaption.text.toString(), userId)

            intent.putExtra("post", post)

            setResult(RESULT_OK, intent)
            finish()
        }
    }
}