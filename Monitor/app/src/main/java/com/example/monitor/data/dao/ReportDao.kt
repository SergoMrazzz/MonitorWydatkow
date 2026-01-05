package com.example.monitor.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.monitor.data.model.Report

@Dao
interface ReportDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReport(report: Report)

    @Update
    suspend fun updateReport(report: Report)

    @Delete
    suspend fun deleteReport(report: Report)

    @Query("SELECT * FROM reports WHERE reportId = :reportId")
    fun getReportById(reportId: String): LiveData<Report?>

    @Query("SELECT * FROM reports WHERE userId = :userId ORDER BY reportId DESC")
    fun getReportsByUserId(userId: String): LiveData<List<Report>>
}