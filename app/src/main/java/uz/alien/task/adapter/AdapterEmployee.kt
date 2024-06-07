package uz.alien.task.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import uz.alien.task.R
import uz.alien.task.databinding.ItemEmployeeBinding
import uz.alien.task.activity.ActivityEmployer
import uz.alien.task.employer.Employer
import uz.alien.task.employer.EmployerRetrofit
import uz.alien.task.employer.EmployerVolley

class AdapterEmployee : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TAG = AdapterEmployee::class.java.simpleName

    fun log(message: String) {
        Log.d(TAG, message)
    }

    val employers = ArrayList<Employer>()

    class EmployeeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemEmployeeBinding.bind(view)
    }

    override fun getItemCount() = employers.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return EmployeeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_employee, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is EmployeeViewHolder) {

            holder.binding.tvName.text = employers[position].name
            holder.binding.tvAge.text = employers[position].age
            holder.binding.tvSalary.text = employers[position].salary + '$'
        }
    }

    fun update(employer: Employer, position: Int) {
        val e = employers[position]
        e.id = employer.id
        e.name = employer.name
        e.salary = employer.salary
        e.age = employer.age
        notifyItemChanged(position)
    }

    fun add(employer: Employer, pos: Int = itemCount) {
        employers.add(pos, employer)
        notifyItemInserted(pos)
        notifyItemRangeChanged(pos, itemCount)
        log("$itemCount is added!")
    }

    fun delete(position: Int) {
        employers.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)
    }

    fun dialogEmployee(employer: Employer, position: Int) {
        AlertDialog.Builder(ActivityEmployer.instance)
            .setTitle("Delete Post")
            .setMessage("Are you sure you want to delete this post?")
            .setPositiveButton(android.R.string.ok) { _, _ ->
                EmployerVolley.delete(employer, position)
            }
            .setNegativeButton(android.R.string.cancel, null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }

    fun clear() {
        employers.clear()
    }
}