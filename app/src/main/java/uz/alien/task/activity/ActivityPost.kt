package uz.alien.task.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postDelayed
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.material.snackbar.Snackbar
import uz.alien.task.adapter.AdapterPost
import uz.alien.task.adapter.RecyclerItemTouchHelper
import uz.alien.task.databinding.ActivityPostBinding
import uz.alien.task.post.Post
import uz.alien.task.post.PostRetrofit


class ActivityPost : AppCompatActivity() {

    lateinit var binding: ActivityPostBinding
    var id = 2
    val adapterPost = AdapterPost()
    lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        instance = this

        handler = Handler(mainLooper)

        handler.postDelayed(3000L) {

            showSnackbar("Swipe Left to Delete Post!")

            handler.postDelayed(3000L) {
                showSnackbar("Swipe Right to Edit Post!")
            }
        }

        binding.rvPosts.layoutManager = LinearLayoutManager(this)
        binding.rvPosts.adapter = adapterPost

        binding.bAddPost.setOnClickListener {
            createLauncher.launch(Intent(this, ActivityCreatePost::class.java))
        }

        val mIth = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: ViewHolder, target: ViewHolder): Boolean {
                val fromPos = viewHolder.adapterPosition
                val toPos = target.adapterPosition

                return false
            }

            override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val post = adapterPost.posts[position]
                if (direction == ItemTouchHelper.LEFT) {
                    PostRetrofit.delete(post, position)
                } else {
                    val intent = Intent(this@ActivityPost, ActivityUpdatePost::class.java)
                    intent.putExtra("position", position)
                    intent.putExtra("post", post)

                    adapterPost.notifyItemChanged(position)

                    handler.postDelayed(546L) {

                        updateLauncher.launch(intent)
                    }
                }
            }
        })

        mIth.attachToRecyclerView(binding.rvPosts)

//        val itemTouchHelper = RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, object : RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
//            override fun onSwiped(viewHolder: ViewHolder, direction: Int, position: Int) {
//                Log.d("@@@@", "$position")
//            }
//        })
//
//        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvPosts)

        PostRetrofit.getAll()
    }

    val createLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            result.data?.let {
                val post = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    it.getSerializableExtra("post", Post::class.java)
                } else {
                    it.getSerializableExtra("post") as Post
                }
                post?.let {
                    PostRetrofit.create(it)
                }
            }
        }
    }

    val updateLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            result.data?.let {
                val position = it.getIntExtra("position", 0)
                val post = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    it.getSerializableExtra("post", Post::class.java)
                } else {
                    it.getSerializableExtra("post") as Post
                }
                post?.let {
                    PostRetrofit.update(position, it)
                }
            }
        }
    }

    companion object {
        lateinit var instance: ActivityPost

        fun showSnackbar(message: String) {
            Snackbar.make(instance.findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show()
        }
    }
}