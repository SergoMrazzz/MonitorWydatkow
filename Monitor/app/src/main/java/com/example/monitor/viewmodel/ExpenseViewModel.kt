package com.example.monitor.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.monitor.data.database.AppDatabase
import com.example.monitor.data.model.Category
import com.example.monitor.data.model.Currency
import com.example.monitor.data.model.Expense
import com.example.monitor.data.model.Note
import com.example.monitor.data.model.Reminder
import com.example.monitor.data.model.Report
import com.example.monitor.data.model.User
import com.example.monitor.data.repository.ExpenseRepository
import kotlinx.coroutines.launch
import java.util.Date

class ExpenseViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ExpenseRepository
    val currentUser: LiveData<User?>
    val allCategories: LiveData<List<Category>>
    val allCurrencies: LiveData<List<Currency>>

    init {
        val database = AppDatabase.getDatabase(application)
        repository = ExpenseRepository(
            database.userDao(),
            database.categoryDao(),
            database.currencyDao(),
            database.noteDao(),
            database.expenseDao(),
            database.reminderDao(),
            database.reportDao()
        )
        currentUser = repository.getCurrentUser()
        allCategories = repository.getAllCategories()
        allCurrencies = repository.getAllCurrencies()
    }

    // User operations
    fun insertUser(user: User) = viewModelScope.launch {
        repository.insertUser(user)
    }

    fun updateUser(user: User) = viewModelScope.launch {
        repository.updateUser(user)
    }

    // Category operations
    fun insertCategory(category: Category) = viewModelScope.launch {
        repository.insertCategory(category)
    }

    fun initializeDefaultCategories() = viewModelScope.launch {
        val categories = listOf(
            Category("cat_1", "Jedzenie", "🍔", "#FF5722"),
            Category("cat_2", "Transport", "🚗", "#2196F3"),
            Category("cat_3", "Rozrywka", "🎬", "#9C27B0"),
            Category("cat_4", "Rachunki", "💡", "#FFC107"),
            Category("cat_5", "Zdrowie", "🏥", "#4CAF50"),
            Category("cat_6", "Zakupy", "🛒", "#FF9800"),
            Category("cat_7", "Edukacja", "📚", "#3F51B5"),
            Category("cat_8", "Inne", "📦", "#9E9E9E")
        )
        repository.insertAllCategories(categories)
    }

    // Currency operations
    fun insertCurrency(currency: Currency) = viewModelScope.launch {
        repository.insertCurrency(currency)
    }

    fun initializeDefaultCurrencies() = viewModelScope.launch {
        val currencies = listOf(
            Currency("PLN", 1.0),
            Currency("USD", 4.0),
            Currency("EUR", 4.5),
            Currency("GBP", 5.2)
        )
        repository.insertAllCurrencies(currencies)
    }

    // Expense operations
    fun insertExpense(expense: Expense) = viewModelScope.launch {
        repository.insertExpense(expense)
    }

    fun updateExpense(expense: Expense) = viewModelScope.launch {
        repository.updateExpense(expense)
    }

    fun deleteExpense(expense: Expense) = viewModelScope.launch {
        repository.deleteExpense(expense)
    }

    fun getExpensesByUserId(userId: String): LiveData<List<Expense>> {
        return repository.getExpensesByUserId(userId)
    }

    fun getExpensesByDateRange(userId: String, startDate: Date, endDate: Date): LiveData<List<Expense>> {
        return repository.getExpensesByDateRange(userId, startDate, endDate)
    }

    fun getExpensesByCategory(userId: String, categoryId: String): LiveData<List<Expense>> {
        return repository.getExpensesByCategory(userId, categoryId)
    }

    fun getExpenseById(expenseId: String): LiveData<Expense?> {
        return repository.getExpenseById(expenseId)
    }


    // Note operations
    fun insertNote(note: Note) = viewModelScope.launch {
        repository.insertNote(note)
    }

    fun getNoteById(noteId: String): LiveData<Note?> {
        return repository.getNoteById(noteId)
    }

    // Reminder operations
    fun insertReminder(reminder: Reminder) = viewModelScope.launch {
        repository.insertReminder(reminder)
    }

    fun updateReminder(reminder: Reminder) = viewModelScope.launch {
        repository.updateReminder(reminder)
    }

    fun deleteReminder(reminder: Reminder) = viewModelScope.launch {
        repository.deleteReminder(reminder)
    }

    fun getRemindersByUserId(userId: String): LiveData<List<Reminder>> {
        return repository.getRemindersByUserId(userId)
    }

    fun getAllReminders(): LiveData<List<Reminder>> {
        return repository.getAllReminders()
    }

    // Report operations
    fun insertReport(report: Report) = viewModelScope.launch {
        repository.insertReport(report)
    }

    fun getReportsByUserId(userId: String): LiveData<List<Report>> {
        return repository.getReportsByUserId(userId)
    }
}