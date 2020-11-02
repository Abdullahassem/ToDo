package com.bignerdranch.android.todo

import java.util.*

data class Task( val id: UUID = UUID.randomUUID(),
                var title: String = "",
                var dueDate: Date = Date(),
                var isCompleted: Boolean = false,
                var creationDate:Date=Date()) {

    val photoFileName
        get() = "IMG_$id.jpg"
}