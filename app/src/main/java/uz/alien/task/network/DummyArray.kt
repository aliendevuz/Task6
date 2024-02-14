package uz.alien.task.network

import com.google.gson.annotations.SerializedName

data class DummyArray(

    @SerializedName("status")
    val status: String? = null,

    @SerializedName("data")
    val data: ArrayList<EmployerResp> = ArrayList(),

    @SerializedName("message")
    val message: String? = null
)