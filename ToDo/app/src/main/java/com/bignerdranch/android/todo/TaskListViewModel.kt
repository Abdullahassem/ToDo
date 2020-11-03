package com.bignerdranch.android.todo


import androidx.lifecycle.ViewModel

class TaskListViewModel :ViewModel() {


private val taskRepository=TaskRepository.get()
    val taskListLiveData=taskRepository.getTasks()

    fun addTasks(task:Task){
        taskRepository.addTask(task)

    }

    }
