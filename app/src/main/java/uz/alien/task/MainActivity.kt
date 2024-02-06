package uz.alien.task

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import uz.alien.task.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bButton.setOnClickListener {

            permissionLauncher.launch(arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ))

            val fileName = "data.txt"

            val cacheFile = File(externalCacheDir, fileName)
            val cacheData = "Hello from Caches!"
            createFile(cacheFile)
            saveData(cacheFile, cacheData)
            binding.tvCaches.text = readData(cacheFile)
            deleteFile(cacheFile)

            val file = File(getExternalFilesDir(null), fileName)
            val fileData = "Hello from Files!"
            createFile(file)
            saveData(file, fileData)
            binding.tvFiles.text = readData(file)
            deleteFile(file)
        }
    }

    val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {}

    fun createFile(file: File) {
        if (!file.exists()) {
            if (file.createNewFile()) Toast.makeText(this, "Creating: File creation successfully", Toast.LENGTH_SHORT).show()
            else Toast.makeText(this, "Creating: File creation failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Creating: File already created", Toast.LENGTH_SHORT).show()
        }
    }

    fun saveData(file: File, data: String) {
        if (file.exists()) {
            try {
                val fileOutputStream = FileOutputStream(file)
                fileOutputStream.write(data.toByteArray(Charset.forName("UTF-8")))
                Toast.makeText(this, "Writing: File writing successfully", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                Toast.makeText(this, "Writing: Something is wrong", Toast.LENGTH_SHORT).show()
            }
        } else Toast.makeText(this, "Writing: File is not exist", Toast.LENGTH_SHORT).show()
    }

    fun readData(file: File): String {
        var data = ""
        if (file.exists()) {
            try {
                val fileInputStream = FileInputStream(file)
                val reader = BufferedReader(InputStreamReader(fileInputStream, "UTF-8"))
                var line: String?
                val lines = ArrayList<String>()
                while (reader.readLine().also { line = it } != null) lines.add(line!!)
                reader.close()
                fileInputStream.close()
                Toast.makeText(this, "Reading: File reading successfully", Toast.LENGTH_SHORT).show()
                for (i in 0 until lines.size) {
                    data += lines[i]
                    if (i != lines.size - 1) data += "\n"
                }
            } catch (e: IOException) {
                Toast.makeText(this, "Reading: Something is wrong", Toast.LENGTH_SHORT).show()
            }
        } else Toast.makeText(this, "Reading: File is not exist", Toast.LENGTH_SHORT).show()
        return data
    }

    fun deleteFile(file: File) {
        if (file.exists()) {
            if (file.delete()) Toast.makeText(this, "Deleting: File removing successfully", Toast.LENGTH_SHORT).show()
            else Toast.makeText(this, "Deleting: File removing failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Deleting: File doesn't exist", Toast.LENGTH_SHORT).show()
        }
    }
}