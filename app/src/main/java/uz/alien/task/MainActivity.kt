package uz.alien.task

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import uz.alien.task.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    var isPermitted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        App.home = this

        isPermitted = checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED

        askPermission()

        binding.bCall.setOnClickListener {
            if (isPermitted) {
                val hash = Uri.encode("#")
                val phoneNumber = binding.etPhone.text.toString().replace("#", hash)
                try {
                    val uri = Uri.parse("tel:$phoneNumber")
                    val callIntent = Intent(Intent.ACTION_CALL, uri)
                    startActivity(callIntent)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this, "Please enter valid phone number", Toast.LENGTH_SHORT).show()
                }
            } else askPermission()
        }
    }

    fun askPermission() {
        if (!isPermitted) {
            if (App.getInt("write permission count") > 2)
                Toast.makeText(this, "Read & Write permission is denied!", Toast.LENGTH_SHORT).show()
            permLauncher.launch(arrayOf(Manifest.permission.CALL_PHONE))
        }
    }

    val permLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
        it[Manifest.permission.CALL_PHONE]?.let { it1 -> isPermitted = it1
            App.saveValue("write permission count", App.getInt("write permission count") + 1)
        }
    }
}