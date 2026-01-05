package com.example.monitor.utils

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.monitor.data.database.AppDatabase
import java.util.*

class ReminderWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val reminderId = inputData.getString("reminderId") ?: return Result.failure()
        val title = inputData.getString("title") ?: "Przypomnienie"
        val message = inputData.getString("message") ?: "Nadszedł termin płatności"

        val notificationHelper = NotificationHelper(applicationContext)
        notificationHelper.sendNotification(title, message, reminderId.hashCode())

        return Result.success()
    }
}