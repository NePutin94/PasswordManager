import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.passwordmanager.R
import com.example.passwordmanager.database.AppDatabase
import com.example.passwordmanager.database.LoginEntity
import com.example.passwordmanager.databinding.FragmentPasswordEditBinding
import com.example.passwordmanager.mvvm.Repository.LoginRepository
import com.example.passwordmanager.PasswordEditViewModel;
import com.example.passwordmanager.PasswordGeneratorDialog
import com.example.passwordmanager.PasswordList

class EditViewModelFactory(private val loginRepository: LoginRepository, private val entityId: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PasswordEditViewModel::class.java)) {
            return PasswordEditViewModel(loginRepository, entityId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class PasswordEdit : Fragment() {
    private lateinit var viewModel: PasswordEditViewModel
    private var loginEntity: Int = 0
    private lateinit var binding: FragmentPasswordEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            loginEntity = it.getInt(ARG_LOGIN_ENTITY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_password_edit, container, false)
        return binding.root
    }

    private fun openPasswordListFragment() {
        val passwordListFragment = PasswordList.newInstance()
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, passwordListFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val loginRepository = LoginRepository(AppDatabase.getDatabase(requireContext()).loginDao())

        val editViewModelFactory = EditViewModelFactory(loginRepository, loginEntity)
        viewModel = ViewModelProvider(this, editViewModelFactory)[PasswordEditViewModel::class.java]

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
                viewModel.update()
                Toast.makeText(context, "Changes are saved!", Toast.LENGTH_SHORT).show()
                openPasswordListFragment()
            }else{
                Toast.makeText(context, "Name and password fields are required", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        const val ARG_LOGIN_ENTITY = "login_entity"

        fun newInstance(loginEntity: LoginEntity): PasswordEdit {
            val fragment = PasswordEdit()
            val args = Bundle()
            args.putInt(ARG_LOGIN_ENTITY, loginEntity.id)
            fragment.arguments = args
            return fragment
        }
    }
}