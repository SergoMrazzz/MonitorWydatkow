package com.example.monitor.data.repository

import androidx.lifecycle.LiveData
import com.example.monitor.data.dao.*
import com.example.monitor.data.model.*
import java.util.Date

class ExpenseRepository(
    private val userDao: UserDao,
    private val categoryDao: CategoryDao,
    private val currencyDao: CurrencyDao,
    private val noteDao: NoteDao,
    private val expenseDao: ExpenseDao,
    private val reminderDao: ReminderDao,
    private val reportDao: ReportDao
) {
    // User
    fun getCurrentUser(): LiveData<User?> = userDao.getCurrentUser()
    suspend fun insertUser(user: User) = userDao.insertUser(user)
    suspend fun updateUser(user: User) = userDao.updateUser(user)

    // Category
    fun getAllCategories(): LiveData<List<Category>> = categoryDao.getAllCategories()
    suspend fun insertCategory(category: Category) = categoryDao.insertCategory(category)
    suspend fun insertAllCategories(categories: List<Category>) = categoryDao.insertAllCategories(categories)

    // Currency
    fun getAllCurrencies(): LiveData<List<Currency>> = currencyDao.getAllCurrencies()
    suspend fun insertCurrency(currency: Currency) = currencyDao.insertCurrency(currency)
    suspend fun insertAllCurrencies(currencies: List<Currency>) = currencyDao.insertAllCurrencies(currencies)

    // Note
    suspend fun insertNote(note: Note) = noteDao.insertNote(note)
    fun getNoteById(noteId: String): LiveData<Note?> = noteDao.getNoteById(noteId)

    // Expense
    suspend fun insertExpense(expense: Expense) = expenseDao.insertExpense(expense)
    suspend fun updateExpense(expense: Expense) = expenseDao.updateExpense(expense)
    suspend fun deleteExpense(expense: Expense) = expenseDao.deleteExpense(expense)
    fun getExpensesByUserId(userId: String): LiveData<List<Expense>> = expenseDao.getExpensesByUserId(userId)

    fun getExpenseById(expenseId: String): LiveData<Expense?> =
        expenseDao.getExpenseById(expenseId)
    fun getExpensesByDateRange(userId: String, startDate: Date, endDate: Date): LiveData<List<Expense>> =
        expenseDao.getExpensesByDateRange(userId, startDate, endDate)
    fun getExpensesByCategory(userId: String, categoryId: String): LiveData<List<Expense>> =
        expenseDao.getExpensesByCategory(userId, categoryId)
    suspend fun getTotalExpensesByDateRange(userId: String, startDate: Date, endDate: Date): Double =
        expenseDao.getTotalExpensesByDateRange(userId, startDate, endDate) ?: 0.0

    // Reminder
    suspend fun insertReminder(reminder: Reminder) = reminderDao.insertReminder(reminder)
    suspend fun updateReminder(reminder: Reminder) = reminderDao.updateReminder(reminder)
    suspend fun deleteReminder(reminder: Reminder) = reminderDao.deleteReminder(reminder)
    fun getRemindersByUserId(userId: String): LiveData<List<Reminder>> = reminderDao.getRemindersByUserId(userId)
    fun getAllReminders(): LiveData<List<Reminder>> = reminderDao.getAllReminders()

    // Report
    suspend fun insertReport(report: Report) = reportDao.insertReport(report)
    fun getReportsByUserId(userId: String): LiveData<List<Report>> = reportDao.getReportsByUserId(userId)
}