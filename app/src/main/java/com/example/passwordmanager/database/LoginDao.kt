package com.example.passwordmanager.database

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface LoginDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: LoginEntity)

    @Update
    fun update(user: LoginEntity)

    @Delete
    fun delete(user: LoginEntity)

    @Query("SELECT * FROM logins")
    fun getAllLogins(): LiveData<List<LoginEntity>>
}