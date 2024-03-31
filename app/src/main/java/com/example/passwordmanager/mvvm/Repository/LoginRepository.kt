package com.example.passwordmanager.mvvm.Repository

import androidx.lifecycle.LiveData
import com.example.passwordmanager.database.LoginDao
import com.example.passwordmanager.database.LoginEntity

class LoginRepository(private val loginDao: LoginDao) {
    val allLogins: LiveData<List<LoginEntity>> = loginDao.getAllLogins()

    fun get(id: Int): LoginEntity {
       return loginDao.getLoginById(id)
    }
    fun insert(login: LoginEntity) {
        loginDao.insert(login)
    }

    fun update(login: LoginEntity) {
        loginDao.update(login)
    }

    fun delete(login: LoginEntity) {
        loginDao.delete(login)
    }
}