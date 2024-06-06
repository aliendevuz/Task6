package uz.alien.task.post

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import uz.alien.task.ActivityPost
import uz.alien.task.R
import uz.alien.task.databinding.ItemPostBinding

class AdapterPost : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TAG = AdapterPost::class.java.simpleName

    fun log(message: String) {
        Log.d(TAG, message)
    }

    private val posts = ArrayList<Post>()

    class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemPostBinding.bind(view)
    }

    override fun getItemCount() = posts.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PostViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is PostViewHolder) {

            holder.binding.root.setOnLongClickListener {
                dialogPost(posts[position], position)
                return@setOnLongClickListener false
            }

            holder.binding.tvTitle.text = posts[position].title.uppercase()
            holder.binding.tvBody.text = posts[position].body
        }
    }

    fun add(post: Post, pos: Int = itemCount) {
        posts.add(pos, post)
        notifyItemInserted(pos)
        notifyItemRangeChanged(pos, itemCount)
        log("$itemCount is added!")
    }

    fun delete(position: Int) {
        posts.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)
    }

    fun dialogPost(post: Post, position: Int) {
        AlertDialog.Builder(ActivityPost.instance)
            .setTitle("Delete Post")
            .setMessage("Are you sure you want to delete this post?")
            .setPositiveButton(android.R.string.ok) { _, _ ->
                PostRetrofit.delete(post, position)
            }
            .setNegativeButton(android.R.string.cancel, null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }
}