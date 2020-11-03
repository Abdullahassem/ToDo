package com.bignerdranch.android.todo

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

private const val TAG="TaskListFragment"

class TaskListFragment :Fragment(){

    interface Callbacks{
        fun onTaskSeleceted(taskId:UUID)
    }

    private var callbacks:Callbacks?=null
    private lateinit var taskRecycleView:RecyclerView
    private var adapter:TaskAdapter?=null

    private val taskListViewModel:TaskListViewModel by lazy{
        ViewModelProviders.of(this).get(TaskListViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks=context as Callbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task_list, container, false)

        taskRecycleView =
          view.findViewById(R.id.task_recycler_view)
        taskRecycleView.layoutManager = LinearLayoutManager(context)

        updateUI()

        return view
    }

    override fun onDetach() {
        super.onDetach()
        callbacks=null
    }
private fun updateUI(){
    val tasks=taskListViewModel.tasks
    adapter= TaskAdapter(tasks)
    taskRecycleView.adapter=adapter
}

    private inner class TaskHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var task: Task

        private val titleTextView: TextView = itemView.findViewById(R.id.task_title)
        private val dateTextView: TextView = itemView.findViewById(R.id.task_date)
        private val completedImageView: ImageView = itemView.findViewById(R.id.task_completed)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(task: Task) {
            this.task = task
            titleTextView.text = this.task.title
            dateTextView.text = this.task.dueDate.toString()
            completedImageView.visibility = if (task.isCompleted) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

        override fun onClick(v: View) {
            Toast.makeText(context, "${task.title} clicked!", Toast.LENGTH_SHORT)
               callbacks?.onTaskSeleceted(task.id)
        }
    }    private inner class TaskAdapter(var tasks:List<Task>):RecyclerView.Adapter<TaskHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
            val view= layoutInflater.inflate(R.layout.list_item_task,parent,false)
            return TaskHolder(view)
        }

        override fun onBindViewHolder(holder: TaskHolder, position: Int) {
            val task = tasks[position]
            holder.bind(task)
        }

        override fun getItemCount()=tasks.size

    }

    companion object{
        fun newInstance(): TaskListFragment{
            return TaskListFragment()
        }
    }
}