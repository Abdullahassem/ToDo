package com.bignerdranch.android.todo

import android.accounts.AccountManager.get
import android.text.AutoText.get
import android.view.ViewConfiguration.get
import androidx.appcompat.view.ActionBarPolicy.get
import androidx.appcompat.widget.AppCompatDrawableManager.get
import androidx.appcompat.widget.ResourceManagerInternal.get
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ProcessLifecycleOwner.get
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.util.*

class TaskListViewModel :ViewModel() {


private val taskRepository=TaskRepository.get()
    val taskListLiveData=taskRepository.getTasks()

    fun addTasks(task:Task){
        taskRepository.addTask(task)

    }

    }
