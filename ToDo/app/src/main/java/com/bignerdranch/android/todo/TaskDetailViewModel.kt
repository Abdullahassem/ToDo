package com.bignerdranch.android.todo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.io.File
import java.util.*
import androidx.lifecycle.*


class TaskDetailViewModel:ViewModel() {
    private val taskRepository=TaskRepository.get()
    private val taskIdLiveData=MutableLiveData<UUID>()

    var taskLiveData: LiveData<Task?> =
            Transformations.switchMap(taskIdLiveData) { taskId ->
                taskRepository.getTask(taskId)
            }

    fun loadTask(taskId: UUID) {
        taskIdLiveData.value = taskId
    }


    fun saveTask(task: Task) {
        taskRepository.updateTask(task)
    }

    fun deleteTask(task: Task){
        taskRepository.deleteTask(task)
    }


}