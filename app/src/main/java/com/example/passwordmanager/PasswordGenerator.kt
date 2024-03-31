package com.example.passwordmanager

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.passwordmanager.database.AppDatabase
import com.example.passwordmanager.databinding.FragmentPasswordGeneratorBinding
import com.example.passwordmanager.mvvm.Repository.LoginRepository

class PasswordGeneratorViewModelFactory(private val loginRepository: LoginRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PasswordGeneratorViewModel::class.java)) {
            return PasswordGeneratorViewModel(loginRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class PasswordGenerator : Fragment() {
    private lateinit var viewModel: PasswordGeneratorViewModel
    private lateinit var binding: FragmentPasswordGeneratorBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_password_generator, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loginRepository = LoginRepository(AppDatabase.getDatabase(requireContext()).loginDao())

        val passwordGeneratorViewModel = PasswordGeneratorViewModelFactory(loginRepository)
        viewModel = ViewModelProvider(this, passwordGeneratorViewModel)[PasswordGeneratorViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.generateButton.setOnClickListener {
            PasswordGeneratorDialog(
                requireActivity(),
                viewModel.password
            ).show(childFragmentManager, "generate_password_dialog")
        }

        binding.saveButton.setOnClickListener {
            if (viewModel.validateInput()) {
                viewModel.insert()
                Toast.makeText(context, "Data has been added!", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(context, "Name and password fields are required", Toast.LENGTH_SHORT).show()
            }
        }
    }
}