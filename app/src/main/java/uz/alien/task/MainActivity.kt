package uz.alien.task

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import uz.alien.task.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    var hasPermitted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hasPermitted = checkCallingOrSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                checkCallingOrSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                checkCallingOrSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

        binding.bCreate.setOnClickListener {

            if (hasPermitted) Toast.makeText(this, "Permissions already allowed!", Toast.LENGTH_SHORT).show()
            else {
                if (App.getInt("camera count") > 2) {
                    Toast.makeText(this, "Camera permission denied!", Toast.LENGTH_SHORT).show()
                }
                if (App.getInt("location count") > 2) {
                    Toast.makeText(this, "Location permission denied!", Toast.LENGTH_SHORT).show()
                }
                permissionLauncher.launch(arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ))
            }
        }
    }

    val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
        it[Manifest.permission.CAMERA]?.let { it1 -> if (!it1) App.saveValue("camera count", App.getInt("camera count") + 1) }
        it[Manifest.permission.ACCESS_FINE_LOCATION]?.let { it1 -> if (!it1) App.saveValue("location count", App.getInt("location count") + 1) }
        it[Manifest.permission.ACCESS_COARSE_LOCATION]?.let { it1 -> if (!it1) App.saveValue("location count", App.getInt("location count") + 1) }
        hasPermitted = it[Manifest.permission.CAMERA] == true &&
                it[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                it[Manifest.permission.ACCESS_COARSE_LOCATION] == true
    }

//    fun createFile(file: File) {
//        if (!file.exists()) {
//            if (file.createNewFile()) setStatus("Creating: File creation successfully\n${file.absolutePath}")
//            else setStatus("Creating: File creation failed\n${file.absolutePath}")
//        } else setStatus("Creating: File already created\n${file.absolutePath}")
//    }
//
//    fun writeData(file: File, data: String) {
//        if (file.exists()) try {
//            val fileOutputStream = FileOutputStream(file)
//            fileOutputStream.write(data.toByteArray(Charset.forName("UTF-8")))
//            setStatus("Writing: File writing successfully\n${file.absolutePath}")
//        } catch (e: IOException) {
//            setStatus("Writing: Something is wrong\n${file.absolutePath}")
//        } else setStatus("Writing: File is not exist\n${file.absolutePath}")
//    }
//
//    fun readData(file: File): String {
//        var data = ""
//        if (file.exists()) try {
//            val fileInputStream = FileInputStream(file)
//            val reader = BufferedReader(InputStreamReader(fileInputStream, "UTF-8"))
//            var line: String?
//            val lines = ArrayList<String>()
//            while (reader.readLine().also { line = it } != null) lines.add(line!!)
//            reader.close()
//            fileInputStream.close()
//            setStatus("Reading: File reading successfully\n${file.absolutePath}")
//            for (i in 0 until lines.size) {
//                data += lines[i]
//                if (i != lines.size - 1) data += "\n"
//            }
//        } catch (e: IOException) {
//            setStatus("Reading: Something is wrong\n${file.absolutePath}")
//        } else setStatus("Reading: File is not exist\n${file.absolutePath}")
//        return data
//    }
//
//    fun deleteFile(file: File) {
//        if (file.exists()) {
//            if (file.delete()) setStatus("Deleting: File removing successfully\n${file.absolutePath}")
//            else setStatus("Deleting: File removing failed\n${file.absolutePath}")
//        } else setStatus("Deleting: File doesn't exist\n${file.absolutePath}")
//    }
}