package com.example.monitor.ui.reminders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.monitor.R
import com.example.monitor.viewmodel.ExpenseViewModel
import com.example.monitor.adapter.ReminderAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.recyclerview.widget.RecyclerView

class RemindersFragment : Fragment() {

    private val viewModel: ExpenseViewModel by activityViewModels()
    private lateinit var reminderAdapter: ReminderAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_reminders, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerViewReminders)
        fab = view.findViewById(R.id.fabAddReminder)

        setupRecyclerView()
        observeData()

        fab.setOnClickListener {
            findNavController().navigate(R.id.action_remindersFragment_to_addReminderFragment)
        }
    }

    private fun setupRecyclerView() {
        reminderAdapter = ReminderAdapter { reminder ->
            viewModel.deleteReminder(reminder)
        }
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = reminderAdapter
    }

    private fun observeData() {
        viewModel.currentUser.observe(viewLifecycleOwner) { user ->
            user?.let {
                viewModel.getRemindersByUserId(it.userId).observe(viewLifecycleOwner) { reminders ->
                    reminderAdapter.submitList(reminders)
                }
            }
        }
    }
}