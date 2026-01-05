package com.example.monitor.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.monitor.data.model.Reminder

@Dao
interface ReminderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminder(reminder: Reminder)

    @Update
    suspend fun updateReminder(reminder: Reminder)

    @Delete
    suspend fun deleteReminder(reminder: Reminder)

    @Query("SELECT * FROM reminders WHERE reminderId = :reminderId")
    fun getReminderById(reminderId: String): LiveData<Reminder?>

    @Query("SELECT * FROM reminders WHERE userId = :userId ORDER BY date ASC")
    fun getRemindersByUserId(userId: String): LiveData<List<Reminder>>

    @Query("SELECT * FROM reminders")
    fun getAllReminders(): LiveData<List<Reminder>>
}