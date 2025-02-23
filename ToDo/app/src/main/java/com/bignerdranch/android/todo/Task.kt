package com.bignerdranch.android.todo

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Task(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    var title: String = "",
    var dueDate: Date = Date(),
    var isCompleted: Boolean = false,
    var creationDate: Date = Date(),
    var description: String = ""
)
