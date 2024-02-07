package uz.alien.task

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import uz.alien.task.databinding.ActivityMainBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    var writeable = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        writeable = checkCallingOrSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

        binding.bShowPictures.setOnClickListener {
            if (writeable) startActivity(Intent(this, PictureActivity::class.java))
            else askWritePermission()
        }

        binding.bTakePhoto.setOnClickListener {
            if (writeable) takePhoto.launch(null) else askWritePermission()
        }
    }

    fun askWritePermission() {
        if (!writeable) {
            if (App.getInt("write permission count") > 2)
                Toast.makeText(this, "Read & Write permission is denied!", Toast.LENGTH_SHORT).show()
            permissionLauncher.launch(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE))
        }
    }

    val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
        it[Manifest.permission.WRITE_EXTERNAL_STORAGE]?.let { it1 -> writeable = it1
            App.saveValue("write permission count", App.getInt("write permission count") + 1)
        }
    }

    val takePhoto = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
        val fileName = System.nanoTime().toString()
        if (savePhoto(fileName, it!!)) Toast.makeText(this, "Photo saved successfully!", Toast.LENGTH_SHORT).show()
        else Toast.makeText(this, "Failed to save photo!", Toast.LENGTH_SHORT).show()
    }

    fun savePhoto(fileName: String, bitmap: Bitmap): Boolean {
        val picturesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val file = File(picturesDir, "$fileName.jpg")

        return try {
            FileOutputStream(file).use {
                if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it))
                    throw IOException("Couldn't save bitmap!")
            }
            true
        } catch (e: IOException) { false }
    }
}