package uz.alien.task

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import uz.alien.task.databinding.ActivityPictureBinding

class PictureActivity : AppCompatActivity() {

    lateinit var binding: ActivityPictureBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPictureBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvPictures.layoutManager = GridLayoutManager(this, 2)
        val pictures = ArrayList<Bitmap>()
        pictures.addAll(getAllImages())
        binding.rvPictures.adapter = AdapterPicture(pictures)
    }

    private fun getAllImages(): ArrayList<Bitmap> {
        val images = ArrayList<Bitmap>()
        val files = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).listFiles()
        if (files != null) {
            for (file in files) {
                val bitmap = BitmapFactory.decodeFile(file.absolutePath)
                if (bitmap != null) {
                    images.add(bitmap)
                }
            }
        }
        return images
    }
}