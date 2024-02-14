package uz.alien.task.network

import com.google.gson.annotations.SerializedName

data class EmployerResp(

    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("employee_name")
    val name: String? = null,

    @SerializedName("employee_salary")
    val salary: Int = 0,

    @SerializedName("employee_age")
    val age: Int = 0,

    @SerializedName("profile_image")
    val profile_image: String = ""
)