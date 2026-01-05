package com.example.monitor.ui.reminders

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.monitor.R
import com.example.monitor.viewmodel.ExpenseViewModel
import com.example.monitor.data.model.Reminder
import com.google.android.material.button.MaterialButton
import java.text.SimpleDateFormat
import java.util.*

class AddReminderFragment : Fragment() {

    private val viewModel: ExpenseViewModel by activityViewModels()
    private var selectedDate = Date()
    private lateinit var buttonSelectDate: MaterialButton
    private lateinit var spinnerFrequency: Spinner
    private lateinit var buttonSave: MaterialButton
    private lateinit var buttonCancel: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_reminder, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonSelectDate = view.findViewById(R.id.buttonSelectDate)
        spinnerFrequency = view.findViewById(R.id.spinnerFrequency)
        buttonSave = view.findViewById(R.id.buttonSave)
        buttonCancel = view.findViewById(R.id.buttonCancel)

        setupSpinner()
        updateDateButton()

        buttonSelectDate.setOnClickListener {
            showDatePicker()
        }

        buttonSave.setOnClickListener {
            saveReminder()
        }

        buttonCancel.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupSpinner() {
        val frequencies = arrayOf("Jednorazowo", "Codziennie", "Co tydzień", "Co miesiąc", "Co rok")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, frequencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFrequency.adapter = adapter
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        calendar.time = selectedDate

        DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                selectedDate = calendar.time
                updateDateButton()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun updateDateButton() {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        buttonSelectDate.text = dateFormat.format(selectedDate)
    }

    private fun saveReminder() {
        val frequency = spinnerFrequency.selectedItem.toString()

        viewModel.currentUser.observe(viewLifecycleOwner) { user ->
            user?.let {
                val reminderId = "reminder_${System.currentTimeMillis()}"
                val reminder = Reminder(
                    reminderId = reminderId,
                    userId = it.userId,
                    date = selectedDate,
                    frequency = frequency
                )

                viewModel.insertReminder(reminder)
                Toast.makeText(context, "Przypomnienie dodane", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }
        }
    }
}