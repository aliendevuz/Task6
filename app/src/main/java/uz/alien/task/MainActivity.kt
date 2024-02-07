package uz.alien.task

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import uz.alien.task.databinding.ActivityMainBinding
import java.io.IOException

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bShowPictures.setOnClickListener {
            startActivity(Intent(this, PictureActivity::class.java))
        }

        binding.bTakePhoto.setOnClickListener {
            takePhoto.launch(null)
        }
    }

    val takePhoto = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
        val fileName = System.nanoTime().toString()
        if (savePhoto(fileName, it!!)) Toast.makeText(this, "Photo saved successfully!", Toast.LENGTH_SHORT).show()
        else Toast.makeText(this, "Failed to save photo!", Toast.LENGTH_SHORT).show()
    }

    fun savePhoto(fileName: String, bitmap: Bitmap) = try {
        openFileOutput("$fileName.jpg", MODE_PRIVATE).use {
            if (!bitmap.compress(Bitmap.CompressFormat.PNG, 100, it))
                throw IOException("Couldn't save bitmap!")
        }
        true
    } catch (e: IOException) { false }
}