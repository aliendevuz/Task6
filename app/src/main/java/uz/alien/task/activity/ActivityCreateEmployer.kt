package uz.alien.task.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import uz.alien.task.databinding.ActivityCreateEmployerBinding
import uz.alien.task.employer.Employer
import uz.alien.task.post.Post

class ActivityCreateEmployer : AppCompatActivity() {

    lateinit var binding: ActivityCreateEmployerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateEmployerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val newId = 20

        binding.bCreateEmployer.setOnClickListener {

            val intent = Intent(this, ActivityPost::class.java)

            val employer = Employer(
                newId,
                binding.etName.text.toString(),
                binding.etSalary.text.toString(),
                binding.etAge.text.toString()
            )

            intent.putExtra("employer", employer)

            setResult(RESULT_OK, intent)
            finish()
        }
    }
}