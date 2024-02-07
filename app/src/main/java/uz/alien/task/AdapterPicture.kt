package uz.alien.task

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.alien.task.databinding.ItemPictureBinding

class AdapterPicture(val pictures: ArrayList<Bitmap>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class PictureViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemPictureBinding.bind(view)
    }

    override fun getItemCount() = pictures.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        PictureViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_picture, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PictureViewHolder) {
            holder.binding.ivPicture.setImageBitmap(pictures[position])
        }
    }
}