package dev_ali_hassan.app.marad.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import dev_ali_hassan.app.marad.databinding.FragmentLoginBinding
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLoginBinding.inflate(inflater)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.loginViewModelFlow.collect { event ->
                when (event) {
                    is LoginViewModel.LoginEvents.FillEmptyFieldPhoneNumber -> {
                        binding.editTextPhoneNumber.error = event.emptyField
                    }
                    is LoginViewModel.LoginEvents.FillEmptyFieldPassword -> {
                        binding.editTextTextPassword.error = event.emptyField
                    }
                }
            }
        }

        binding.loginButton.setOnClickListener {
            viewModel.loginButtonClick(
                binding.editTextPhoneNumber.text.toString(),
                binding.editTextTextPassword.text.toString()
            )
        }
        return binding.root
    }
}