package uz.alien.task.post

import java.io.Serializable

open class Post (
    var id: Int,
    var title: String,
    var body: String,
    var userId: Int
) : Serializable