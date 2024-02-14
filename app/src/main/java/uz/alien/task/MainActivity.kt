package uz.alien.task

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permLauncher.launch(arrayOf(
                Manifest.permission.POST_NOTIFICATIONS
            ))
        }
        isPermitted = checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED

        askPermission()

        binding.bStart.setOnClickListener {
            startService(Intent(this, ReceiverService::class.java))
        }

        binding.bStop.setOnClickListener {
            stopService(Intent(this, ReceiverService::class.java))
        }
    }

    fun askPermission() {
        if (!isPermitted) {
            if (App.getInt("write permission count") > 2)
                Toast.makeText(this, "Read & Write permission is denied!", Toast.LENGTH_SHORT).show()
            permLauncher.launch(arrayOf(Manifest.permission.READ_PHONE_STATE))
        }
    }

    val permLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
        it[Manifest.permission.CALL_PHONE]?.let { it1 -> isPermitted = it1
            App.saveValue("write permission count", App.getInt("write permission count") + 1)
        }
    }
}