package com.example.passwordmanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.passwordmanager.database.AppDatabase
import com.example.passwordmanager.database.LoginEntity
import com.example.passwordmanager.mvvm.Repository.LoginRepository
import com.example.passwordmanager.mvvm.adapters.LoginListAdapter

class LoginViewModelFactory(private val loginRepository: LoginRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(loginRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class PasswordList : Fragment() {
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var loginListAdapter: LoginListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_password_list, container, false)
        loginListAdapter = LoginListAdapter(object : LoginListAdapter.OnItemClickListener {
            override fun onItemClick(login: LoginEntity) {
                openPasswordEditFragment(login)
            }
        })
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = loginListAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        return view
    }

    private fun openPasswordEditFragment(login: LoginEntity) {
        val passwordEditFragment = PasswordEdit.newInstance(login)
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, passwordEditFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loginRepository = LoginRepository(AppDatabase.getDatabase(requireContext()).loginDao())
        val loginViewModelFactory = LoginViewModelFactory(loginRepository)
        loginViewModel = ViewModelProvider(this, loginViewModelFactory)[LoginViewModel::class.java]

        loginViewModel.allLogins.observe(viewLifecycleOwner, Observer { logins ->
            loginListAdapter.setLogins(logins)
        })
    }
}