package com.example.passwordmanager

import android.text.SpannableString
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passwordmanager.database.LoginEntity
import com.example.passwordmanager.mvvm.Repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PasswordGeneratorViewModel (private val loginRepository: LoginRepository) : ViewModel() {
    val name: MutableLiveData<String> = MutableLiveData()
    val email: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()
    val url: MutableLiveData<String> = MutableLiveData()
    val note: MutableLiveData<String> = MutableLiveData()

    fun validateInput(): Boolean {
        if (name.value.isNullOrEmpty()) {
            return false
        }
        if (password.value.isNullOrEmpty()) {
            return false
        }
        return true
    }

    fun insert() {
        val updatedEntity = LoginEntity(
            0,
            name.value ?: "",
            email.value ?: "",
            password.value ?:"",
            url.value ?: "",
            note.value ?: ""
        )
        viewModelScope.launch(Dispatchers.IO) {
            loginRepository.insert(updatedEntity)
        }
    }
}