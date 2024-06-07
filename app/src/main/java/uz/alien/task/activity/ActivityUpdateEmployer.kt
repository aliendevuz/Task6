package uz.alien.task.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import uz.alien.task.databinding.ActivityUpdateEmployerBinding
import uz.alien.task.employer.Employer
import uz.alien.task.post.Post

class ActivityUpdateEmployer : AppCompatActivity() {

    lateinit var binding: ActivityUpdateEmployerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateEmployerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val newId = 20

        val position = intent.getIntExtra("position", 0)
        val employer = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("employer", Employer::class.java)
        } else {
            intent.getSerializableExtra("employer") as Employer
        }

        binding.etName.setText(employer!!.name)
        binding.etName.setSelection(binding.etName.text.length)
        binding.etAge.setText(employer.age)
        binding.etAge.setSelection(binding.etAge.text.length)
        binding.etSalary.setText(employer.salary)
        binding.etSalary.setSelection(binding.etSalary.text.length)

        binding.bUpdateEmployer.setOnClickListener {

            val intent = Intent(this, ActivityPost::class.java)

            val employer = Employer(
                newId,
                binding.etName.text.toString(),
                binding.etSalary.text.toString(),
                binding.etAge.text.toString()
            )

            intent.putExtra("position", position)
            intent.putExtra("employer", employer)

            setResult(RESULT_OK, intent)
            finish()
        }
    }
}