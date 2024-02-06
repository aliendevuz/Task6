package uz.alien.task

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
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
    var writeable = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cacheFile = File(externalCacheDir, fileName)
        val fileFile = File("${Environment.getExternalStorageDirectory().absolutePath}/${Environment.DIRECTORY_DOCUMENTS}", fileName)

        writeable = checkCallingOrSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

        binding.scCache.setOnCheckedChangeListener { _, isChecked -> isCache = isChecked }

        binding.bCreate.setOnClickListener {
            if (isCache) createFile(cacheFile)
            else if (writeable) createFile(fileFile) else askWritePermission()
        }

        binding.bWrite.setOnClickListener {
            if (isCache) writeData(cacheFile, cacheData)
            else if (writeable) writeData(fileFile, fileData) else askWritePermission()
        }

        binding.bRead.setOnClickListener {
            binding.tvData.text = (if (isCache) readData(cacheFile)
            else if (writeable) readData(fileFile) else { askWritePermission(); ""})
        }

        binding.bDelete.setOnClickListener {
            if (isCache) deleteFile(cacheFile)
            else if (writeable) deleteFile(fileFile) else askWritePermission()
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