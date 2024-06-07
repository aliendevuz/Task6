package uz.alien.task.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postDelayed
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import uz.alien.task.adapter.AdapterEmployee
import uz.alien.task.databinding.ActivityEmployeerBinding
import uz.alien.task.employer.Employer
import uz.alien.task.employer.EmployerRetrofit
import uz.alien.task.employer.EmployerVolley
import uz.alien.task.post.Post
import uz.alien.task.post.PostRetrofit

class ActivityEmployer : AppCompatActivity() {

    lateinit var binding: ActivityEmployeerBinding
    var id = 2
    val adapterEmployee = AdapterEmployee()
    lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmployeerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        instance = this

        handler = Handler(mainLooper)

        handler.postDelayed(3000L) {

            showSnackbar("Swipe Left to Delete Employer!")

            handler.postDelayed(3000L) {
                showSnackbar("Swipe Right to Edit Employer!")
            }
        }

        binding.rvEmployes.layoutManager = LinearLayoutManager(this)
        binding.rvEmployes.adapter = adapterEmployee

        binding.root.setOnClickListener {
            if (adapterEmployee.employers.isEmpty())
                EmployerRetrofit.getAll()
        }

        binding.bAddEmployee.setOnClickListener {
            createLauncher.launch(Intent(this, ActivityCreateEmployer::class.java))
        }

        val mIth = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                val fromPos = viewHolder.adapterPosition
                val toPos = target.adapterPosition

                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val employer = adapterEmployee.employers[position]
                if (direction == ItemTouchHelper.LEFT) {
                    EmployerRetrofit.delete(employer, position)
                } else {
                    val intent = Intent(this@ActivityEmployer, ActivityUpdateEmployer::class.java)
                    intent.putExtra("position", position)
                    intent.putExtra("employer", employer)

                    adapterEmployee.notifyItemChanged(position)

                    handler.postDelayed(546L) {

                        updateLauncher.launch(intent)
                    }
                }
            }
        })

        mIth.attachToRecyclerView(binding.rvEmployes)

        EmployerRetrofit.getAll()
    }

    val createLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            result.data?.let {
                val employer = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    it.getSerializableExtra("employer", Employer::class.java)
                } else {
                    it.getSerializableExtra("employer") as Employer
                }
                employer?.let {
                    EmployerRetrofit.create(it)
                }
            }
        }
    }

    val updateLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            result.data?.let {
                val position = it.getIntExtra("position", 0)
                val employer = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    it.getSerializableExtra("employer", Employer::class.java)
                } else {
                    it.getSerializableExtra("employer") as Employer
                }
                employer?.let {
                    EmployerRetrofit.update(position, it)
                }
            }
        }
    }

    companion object {
        lateinit var instance: ActivityEmployer

        fun showSnackbar(message: String) {
            Snackbar.make(instance.findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show()
        }
    }
}