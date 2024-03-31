package com.example.passwordmanager.mvvm.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passwordmanager.database.LoginEntity
import com.example.passwordmanager.mvvm.Repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {
    val allLogins: LiveData<List<LoginEntity>> = loginRepository.allLogins

    fun insert(login: LoginEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            loginRepository.insert(login)
        }
    }

    fun update(login: LoginEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            loginRepository.update(login)
        }
    }

    fun delete(login: LoginEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            loginRepository.delete(login)
        }
    }
}