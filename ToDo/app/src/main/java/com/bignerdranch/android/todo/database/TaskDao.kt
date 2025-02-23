package com.bignerdranch.android.todo.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bignerdranch.android.todo.Task
import java.util.*


@Dao
interface TaskDao {

    @Query("SELECT * FROM task")
    fun getTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM task WHERE id=(:id)")
    fun getTask(id: UUID): LiveData<Task?>

    @Update
    fun updateTask(task: Task)

    @Insert
    fun addTask(task: Task)

    @Delete
    fun deleteTask(task: Task)
}