package uz.alien.task

import android.graphics.Bitmap
import android.os.Bundle
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
        binding.rvPictures.adapter = AdapterPicture(pictures)
    }
}