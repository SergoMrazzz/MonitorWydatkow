package com.example.monitor.ui.expenses

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
import com.example.monitor.data.model.Category
import com.example.monitor.data.model.Currency
import com.example.monitor.data.model.Expense
import com.example.monitor.data.model.Note
import com.example.monitor.viewmodel.ExpenseViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AddExpenseFragment : Fragment() {

    private val viewModel: ExpenseViewModel by activityViewModels()

    private var selectedDate = Date()
    private var categories = listOf<Category>()
    private var currencies = listOf<Currency>()

    private var editingExpenseId: String? = null
    private var editingExpense: Expense? = null
    private var originalNoteId: String? = null


    private lateinit var editTextAmount: TextInputEditText
    private lateinit var spinnerCategory: Spinner
    private lateinit var spinnerCurrency: Spinner
    private lateinit var buttonSelectDate: MaterialButton
    private lateinit var editTextNote: TextInputEditText
    private lateinit var buttonSaveExpense: MaterialButton
    private lateinit var buttonCancel: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_expense, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextAmount = view.findViewById(R.id.editTextAmount)
        spinnerCategory = view.findViewById(R.id.spinnerCategory)
        spinnerCurrency = view.findViewById(R.id.spinnerCurrency)
        buttonSelectDate = view.findViewById(R.id.buttonSelectDate)
        editTextNote = view.findViewById(R.id.editTextNote)
        buttonSaveExpense = view.findViewById(R.id.buttonSaveExpense)
        buttonCancel = view.findViewById(R.id.buttonCancel)

        editingExpenseId = arguments?.getString("expenseId")
        if (editingExpenseId != null) {
            loadExpenseForEdit(editingExpenseId!!)
        }

        updateDateButton()
        observeData()

        buttonSelectDate.setOnClickListener {
            showDatePicker()
        }

        buttonSaveExpense.setOnClickListener {
            saveExpense()
        }

        buttonCancel.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun loadExpenseForEdit(expenseId: String) {
        viewModel.getExpenseById(expenseId).observe(viewLifecycleOwner) { expense ->
            if (expense != null) {
                editingExpense = expense
                editingExpenseId = expense.expenseId

                selectedDate = expense.date
                updateDateButton()

                editTextAmount.setText(expense.amount.toString())

                // если категории уже загружены – сразу выставим позицию
                if (categories.isNotEmpty()) {
                    val index = categories.indexOfFirst { it.categoryId == expense.categoryId }
                    if (index >= 0) spinnerCategory.setSelection(index)
                }

                if (currencies.isNotEmpty()) {
                    val index = currencies.indexOfFirst { it.code == expense.currencyCode }
                    if (index >= 0) spinnerCurrency.setSelection(index)
                }

                originalNoteId = expense.noteId
                expense.noteId?.let { noteId ->
                    viewModel.getNoteById(noteId).observe(viewLifecycleOwner) { note ->
                        editTextNote.setText(note?.content ?: "")
                    }
                }
            }
        }
    }


    private fun observeData() {
        viewModel.allCategories.observe(viewLifecycleOwner) { categoryList ->
            categories = categoryList
            val categoryNames = ArrayList(categoryList.map { it.name })
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                categoryNames
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerCategory.adapter = adapter
        }

        editingExpense?.let { exp ->
            val index = categories.indexOfFirst { it.categoryId == exp.categoryId }
            if (index >= 0) spinnerCategory.setSelection(index)
        }

        viewModel.allCurrencies.observe(viewLifecycleOwner) { currencyList ->
            currencies = currencyList
            val currencyCodes = ArrayList(currencyList.map { it.code })
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                currencyCodes
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerCurrency.adapter = adapter
        }

        editingExpense?.let { exp ->
            val index = currencies.indexOfFirst { it.code == exp.currencyCode }
            if (index >= 0) spinnerCurrency.setSelection(index)
        }
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

    private fun saveExpense() {
        val amountText = editTextAmount.text.toString()
        val noteText = editTextNote.text.toString()

        if (amountText.isEmpty()) {
            Toast.makeText(context, "Wprowadź kwotę", Toast.LENGTH_SHORT).show()
            return
        }

        val amount = amountText.toDoubleOrNull()
        if (amount == null || amount <= 0) {
            Toast.makeText(context, "Nieprawidłowa kwota", Toast.LENGTH_SHORT).show()
            return
        }

        val categoryPosition = spinnerCategory.selectedItemPosition
        if (categoryPosition < 0 || categoryPosition >= categories.size) {
            Toast.makeText(context, "Wybierz kategorię", Toast.LENGTH_SHORT).show()
            return
        }

        val currencyPosition = spinnerCurrency.selectedItemPosition
        if (currencyPosition < 0 || currencyPosition >= currencies.size) {
            Toast.makeText(context, "Wybierz walutę", Toast.LENGTH_SHORT).show()
            return
        }

        val selectedCategoryId = categories[categoryPosition].categoryId
        val selectedCurrencyCode = currencies[currencyPosition].code

        viewModel.currentUser.observe(viewLifecycleOwner) { user ->
            user?.let {
                val expenseId = editingExpenseId ?: "exp_${System.currentTimeMillis()}"
                var noteId: String? = null

                if (noteText.isNotEmpty()) {
                    val currentNoteId = originalNoteId ?: "note_${System.currentTimeMillis()}"
                    val note = Note(currentNoteId, noteText)
                    viewModel.insertNote(note)   // insert с REPLACE в DAO
                    noteId = currentNoteId
                } else {
                    noteId = null
                }

                val expense = Expense(
                    expenseId = expenseId,
                    userId = it.userId,
                    amount = amount,
                    date = selectedDate,
                    categoryId = selectedCategoryId,
                    currencyCode = selectedCurrencyCode,
                    noteId = noteId
                )

                if (editingExpenseId == null) {
                    viewModel.insertExpense(expense)
                    Toast.makeText(context, "Wydatek dodany", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.updateExpense(expense)
                    Toast.makeText(context, "Wydatek zaktualizowany", Toast.LENGTH_SHORT).show()
                }

                findNavController().navigateUp()
            }
        }
    }
}