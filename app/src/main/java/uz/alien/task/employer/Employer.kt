package uz.alien.task.employer

import java.io.Serializable

data class Employer(
    var id: Int,
    var name: String,
    var salary: String,
    var age: String
) : Serializable