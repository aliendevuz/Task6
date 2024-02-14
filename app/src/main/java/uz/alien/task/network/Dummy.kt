package uz.alien.task.network

import com.google.gson.annotations.SerializedName

data class Dummy(

    @SerializedName("status")
    val status: String? = null,

    @SerializedName("data")
    val data: EmployerResp? = null,

    @SerializedName("message")
    val message: String? = null
)