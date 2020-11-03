package com.bignerdranch.android.todo

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bignerdranch.android.todo.TaskRepository
import com.bignerdranch.android.todo.database.TaskDatabase
import com.bignerdranch.android.todo.database.migration_1_2
import java.io.File
import java.lang.IllegalStateException
import java.util.*
import java.util.concurrent.Executors

private const val DATABASE_NAME="task-database"

class TaskRepository private constructor(context: Context){
    private val database: TaskDatabase =Room.databaseBuilder(
        context.applicationContext,TaskDatabase::class.java, DATABASE_NAME)
        .addMigrations(migration_1_2)
        .build()

    private val taskDao=database.taskDao()
    private val executor=Executors.newSingleThreadExecutor()
    private val filesDir=context.applicationContext.filesDir

    fun getCrimes(): LiveData<List<Task>> = taskDao.getTasks()
    fun updateTask(task:Task){
        executor.execute{
            taskDao.updateTask(task)
        }
    }

    fun addTask(task:Task){
        executor.execute{
            taskDao.addTask(task)
        }
    }


    companion object{
        private var INSTANCE:TaskRepository?=null

        fun initialize(context: Context){
            if(INSTANCE==null)
                INSTANCE= TaskRepository(context)
        }
        fun get():TaskRepository{
            return INSTANCE?:
            throw IllegalStateException("TaskRepo must be initialized")
        }
    }



}