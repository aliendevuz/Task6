package uz.alien.task.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import uz.alien.task.databinding.ActivityUpdatePostBinding
import uz.alien.task.post.Post

class ActivityUpdatePost : AppCompatActivity() {

    lateinit var binding: ActivityUpdatePostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdatePostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val newId = 20
        val userId = 45

        val position = intent.getIntExtra("position", 0)
        val post = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("post", Post::class.java)
        } else {
            intent.getSerializableExtra("post") as Post
        }

        binding.etTitle.setText(post!!.title)
        binding.etTitle.setSelection(binding.etTitle.text.length - 1)
        binding.etCaption.setText(post!!.body)
        binding.etCaption.setSelection(binding.etTitle.text.length - 1)

        binding.bUpdatePost.setOnClickListener {

            val intent = Intent(this, ActivityPost::class.java)

            val post = Post(newId, binding.etTitle.text.toString(), binding.etCaption.text.toString(), userId)

            intent.putExtra("position", position)
            intent.putExtra("post", post)

            setResult(RESULT_OK, intent)
            finish()
        }
    }
}