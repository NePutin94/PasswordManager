package com.example.passwordmanager.mvvm.Repository

import androidx.lifecycle.LiveData
import com.example.passwordmanager.database.LoginDao
import com.example.passwordmanager.database.LoginEntity

class LoginRepository(private val loginDao: LoginDao) {
    val allLogins: LiveData<List<LoginEntity>> = loginDao.getAllLogins()

    suspend fun insert(login: LoginEntity) {
        loginDao.insert(login)
    }

    suspend fun update(login: LoginEntity) {
        loginDao.update(login)
    }

    suspend fun delete(login: LoginEntity) {
        loginDao.delete(login)
    }
}