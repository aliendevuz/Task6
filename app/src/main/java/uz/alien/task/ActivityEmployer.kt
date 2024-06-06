package uz.alien.task

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import uz.alien.task.databinding.ActivityEmployeerBinding
import uz.alien.task.employer.AdapterEmployee
import uz.alien.task.employer.Employer
import uz.alien.task.employer.EmployerRetrofit

class ActivityEmployer : AppCompatActivity() {

    lateinit var binding: ActivityEmployeerBinding
    var id = 2
    val adapterEmployee = AdapterEmployee()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmployeerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        instance = this

        binding.rvEmployes.layoutManager = LinearLayoutManager(this)
        binding.rvEmployes.isNestedScrollingEnabled = false
        binding.rvEmployes.adapter = adapterEmployee

        binding.root.setOnClickListener {
            EmployerRetrofit.getAll()
        }

        binding.bAddEmployee.setOnClickListener {
            EmployerRetrofit.create(Employer(0, "Khalilov Ibrohim", "100", "20"))
        }

        EmployerRetrofit.getAll()
    }

    fun showSnackbar(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show()
    }

    companion object {
        lateinit var instance: ActivityEmployer
    }
}