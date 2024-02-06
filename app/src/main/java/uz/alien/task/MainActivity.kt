package uz.alien.task

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
    var isCache = true
    val fileName = "data.txt"
    val cacheData = "Wrote to Caches!"
    val fileData = "Wrote to Files!"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cacheFile = File(cacheDir, fileName)
        val fileFile = File(filesDir, fileName)

        binding.scCache.setOnCheckedChangeListener { buttonView, isChecked ->
            isCache = isChecked
        }

        binding.bCreate.setOnClickListener {
            createFile(if (isCache) cacheFile else fileFile)
        }

        binding.bWrite.setOnClickListener {
            writeData(if (isCache) cacheFile else fileFile, if (isCache) cacheData else fileData)
        }

        binding.bRead.setOnClickListener {
            binding.tvData.text = readData(if (isCache) cacheFile else fileFile)
        }

        binding.bDelete.setOnClickListener {
            deleteFile(if (isCache) cacheFile else fileFile)
        }


//        .setOnClickListener {
//
//            permissionLauncher.launch(arrayOf(
//                Manifest.permission.CAMERA,
//                Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.ACCESS_COARSE_LOCATION,
//                Manifest.permission.READ_EXTERNAL_STORAGE,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE
//            ))
//        }
    }

//    val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {}

    fun setStatus(status: String) {
        binding.tvStatus.text = status
    }

    fun createFile(file: File) {
        if (!file.exists()) {
            if (file.createNewFile()) setStatus("Creating: File creation successfully\n${file.absolutePath}")
            else setStatus("Creating: File creation failed\n${file.absolutePath}")
        } else setStatus("Creating: File already created\n${file.absolutePath}")
    }

    fun writeData(file: File, data: String) {
        if (file.exists()) try {
            val fileOutputStream = FileOutputStream(file)
            fileOutputStream.write(data.toByteArray(Charset.forName("UTF-8")))
            setStatus("Writing: File writing successfully\n${file.absolutePath}")
        } catch (e: IOException) {
            setStatus("Writing: Something is wrong\n${file.absolutePath}")
        } else setStatus("Writing: File is not exist\n${file.absolutePath}")
    }

    fun readData(file: File): String {
        var data = ""
        if (file.exists()) try {
            val fileInputStream = FileInputStream(file)
            val reader = BufferedReader(InputStreamReader(fileInputStream, "UTF-8"))
            var line: String?
            val lines = ArrayList<String>()
            while (reader.readLine().also { line = it } != null) lines.add(line!!)
            reader.close()
            fileInputStream.close()
            setStatus("Reading: File reading successfully\n${file.absolutePath}")
            for (i in 0 until lines.size) {
                data += lines[i]
                if (i != lines.size - 1) data += "\n"
            }
        } catch (e: IOException) {
            setStatus("Reading: Something is wrong\n${file.absolutePath}")
        } else setStatus("Reading: File is not exist\n${file.absolutePath}")
        return data
    }

    fun deleteFile(file: File) {
        if (file.exists()) {
            if (file.delete()) setStatus("Deleting: File removing successfully\n${file.absolutePath}")
            else setStatus("Deleting: File removing failed\n${file.absolutePath}")
        } else setStatus("Deleting: File doesn't exist\n${file.absolutePath}")
    }
}