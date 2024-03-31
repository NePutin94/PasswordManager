package com.example.passwordmanager

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passwordmanager.database.LoginEntity
import com.example.passwordmanager.mvvm.Repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PasswordEditViewModel(private val loginRepository: LoginRepository, private val loginEntityId: Int) : ViewModel() {

    var loginEntity : LoginEntity? = null
    val name: MutableLiveData<String> = MutableLiveData()
    val email: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()
    val url: MutableLiveData<String> = MutableLiveData()
    val note: MutableLiveData<String> = MutableLiveData()

    private fun LoadEntity() {
        viewModelScope.launch(Dispatchers.IO) {
            val entity = loginRepository.get(loginEntityId)
            withContext(Dispatchers.Main) {
                entity.let {
                    name.value = it.name
                    email.value = it.email
                    password.value = it.password
                    url.value = it.url
                    note.value = it.note
                }
            }
        }
    }
    init {
        LoadEntity()
    }

    fun update() {
        val updatedEntity = LoginEntity(
            loginEntityId,
            name.value ?: "",
            email.value ?: "",
            password.value ?: "",
            url.value ?: "",
            note.value ?: ""
        )
        viewModelScope.launch(Dispatchers.IO) {
            loginRepository.insert(updatedEntity)
        }
    }
}