package com.bignerdranch.android.todo

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import java.util.*

private const val TAG = "TaskFragment"
private const val ARG_TASK_ID = "task_id"
private const val DIALOG_DATE = "DialogDate"
private const val REQUEST_DATE = 0


class TaskFragment : Fragment(), DatePickerFragment.Callbacks {

    private lateinit var task: Task
    private lateinit var title: EditText
    private lateinit var dateButton: Button
    private lateinit var isCompletedCheck: CheckBox
    private lateinit var description: EditText
    private lateinit var seconDatebutton: Button
    private lateinit var deleteButton: Button
    private val taskDetailViewModel: TaskDetailViewModel by lazy {
        ViewModelProviders.of(this).get(TaskDetailViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        task = Task()
        val taskId: UUID = arguments?.getSerializable(ARG_TASK_ID) as UUID
        taskDetailViewModel.loadTask(taskId)

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task, container, false)
        seconDatebutton = view.findViewById(R.id.task_date) as Button
        dateButton = view.findViewById(R.id.task_dueDate) as Button
        isCompletedCheck = view.findViewById(R.id.task_completed) as CheckBox
        title = view.findViewById(R.id.task_title) as EditText
        description = view.findViewById(R.id.task_description) as EditText
        deleteButton = view.findViewById(R.id.delete_task)


        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val taskId = arguments?.getSerializable(ARG_TASK_ID) as UUID
        taskDetailViewModel.loadTask(taskId)
        taskDetailViewModel.taskLiveData.observe(
            viewLifecycleOwner,
            Observer { task ->
                task?.let {
                    this.task = task
                    updateUI()
                }
            })
        val appCompatActivity = activity as AppCompatActivity
        appCompatActivity.supportActionBar?.setTitle(R.string.new_task)
    }

    override fun onStart() {
        super.onStart()

        val titleWatcher = object : TextWatcher {

            override fun beforeTextChanged(
                sequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                sequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                task.title = sequence.toString()
            }

            override fun afterTextChanged(sequence: Editable?) {
            }
        }
        val descriptionWatcher = object : TextWatcher {

            override fun beforeTextChanged(
                sequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                sequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                task.description = sequence.toString()
            }

            override fun afterTextChanged(sequence: Editable?) {
            }
        }

        title.addTextChangedListener(titleWatcher)
        description.addTextChangedListener(descriptionWatcher)
        isCompletedCheck.apply {
            setOnCheckedChangeListener { _, isChecked ->
                task.isCompleted = isChecked
            }

        }

        dateButton.setOnClickListener {
            DatePickerFragment.newInstance(task.creationDate).apply {
                setTargetFragment(this@TaskFragment, REQUEST_DATE)
                show(this@TaskFragment.requireFragmentManager(), DIALOG_DATE)
            }
        }

        dateButton.apply {
            text = task.creationDate.toString()
        }

        seconDatebutton.apply {
            text = task.dueDate.toString()
        }



        deleteButton.setOnClickListener {
            taskDetailViewModel.deleteTask(task)
            requireActivity().onBackPressed()
        }

    }

    override fun onStop() {
        super.onStop()
        taskDetailViewModel.saveTask(task)
    }


    override fun onDateSelected(date: Date) {
        task.dueDate = date
        updateUI()
    }

    private fun updateUI() {
        title.setText(task.title)
        dateButton.text = task.dueDate.toString()
        description.setText(task.description)
        isCompletedCheck.isChecked = task.isCompleted
    }


    companion object {
        fun newInstance(taskId: UUID): TaskFragment {
            val args = Bundle().apply {
                putSerializable(ARG_TASK_ID, taskId)
            }
            return TaskFragment().apply {
                arguments = args
            }
        }
    }

}