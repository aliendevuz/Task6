package uz.alien.task

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import uz.alien.task.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        App.home = this

        permLauncher.launch(arrayOf(
            Manifest.permission.RECEIVE_BOOT_COMPLETED
        ))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permLauncher.launch(arrayOf(
                Manifest.permission.POST_NOTIFICATIONS
            ))
        }

        binding.bStartReceiving.setOnClickListener {
            startService(Intent(this, ReceiverService::class.java))
        }

        binding.bStopReceiving.setOnClickListener {
            stopService(Intent(this, ReceiverService::class.java))
        }
    }

    val permLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {}
}