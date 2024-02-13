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
    var isPermitted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        App.home = this

        isPermitted = checkSelfPermission(Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permLauncher.launch(arrayOf(
                Manifest.permission.POST_NOTIFICATIONS
            ))
        }

        askPermission()

        binding.bStartReceiving.setOnClickListener {
            startService(Intent(this, ReceiverService::class.java))
        }

        binding.bStopReceiving.setOnClickListener {
            stopService(Intent(this, ReceiverService::class.java))
        }
    }

    fun askPermission() {
        if (!isPermitted) {
            if (App.getInt("receive sms permission count") > 2)
                Toast.makeText(this, "Receive SMS permission is denied!", Toast.LENGTH_SHORT).show()
            permLauncher.launch(arrayOf(
                Manifest.permission.RECEIVE_SMS
            ))
        }
    }

    val permLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
        it[Manifest.permission.RECEIVE_SMS]?.let { it1 -> isPermitted = it1
            App.saveValue("receive sms permission count", App.getInt("receive sms permission count") + 1)
            if (!isPermitted) askPermission()
        }
    }
}