package com.example.monitor.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.monitor.data.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT * FROM users WHERE userId = :userId")
    fun getUserById(userId: String): LiveData<User?>

    @Query("SELECT * FROM users LIMIT 1")
    fun getCurrentUser(): LiveData<User?>

    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<User>
}