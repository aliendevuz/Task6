package uz.alien.task

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import uz.alien.task.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bStart.setOnClickListener {
            val service = Intent(this, ServiceMedia::class.java)
            startService(service)
        }
        binding.bStop.setOnClickListener {
            val service = Intent(this, ServiceMedia::class.java)
            stopService(service)
        }
    }
}