package com.bignerdranch.android.todo

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.*
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import java.io.File
import java.util.*

private const val TAG="TaskFragment"
private const val ARG_TASK_ID="task_id"

class TaskFragment : Fragment() {

    private lateinit var task: Task
    private lateinit var title: EditText
    private lateinit var dateButton: Button
    private lateinit var isCompletedCheck: CheckBox
    private lateinit var creationDateButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        task = Task()
        val taskId:UUID=arguments?.getSerializable(ARG_TASK_ID)as UUID

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task, container, false)
        dateButton = view.findViewById(R.id.task_date) as Button
        isCompletedCheck = view.findViewById(R.id.task_completed) as CheckBox
        title = view.findViewById(R.id.task_title) as EditText
        dateButton.apply {
            text = task.dueDate.toString()
        }

        return view
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

        title.addTextChangedListener(titleWatcher)
        isCompletedCheck.apply {
            setOnCheckedChangeListener { _, isChecked ->
                task.isCompleted = isChecked
            }

        }

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