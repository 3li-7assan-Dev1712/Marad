package dev_ali_hassan.app.marad.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev_ali_hassan.app.marad.R
import dev_ali_hassan.app.marad.databinding.FragmentLoginBinding
import dev_ali_hassan.app.marad.databinding.FragmentRegisterBinding
import dev_ali_hassan.app.marad.ui.phoneverification.Events
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlin.system.measureTimeMillis

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRegisterBinding.inflate(inflater)



        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.loginViewModelFlow.collect {event ->
                when (event) {
                    RegisterEvents.EmptyName -> binding.userNameEditText.error = "please enter your name"
                    RegisterEvents.EmptyPassword -> binding.userPasswordET.error = "please enter a password"
                    RegisterEvents.EmptyPhoneNumber -> binding.userPhoneNumberEditText.error = "please enter your phone number"
                    is RegisterEvents.ShowPhoneVerificationDialog -> {
                        val bundle = bundleOf()
                        bundle.putString("name", event.name)
                        bundle.putString("email", event.email)
                        bundle.putString("password", event.password)
                        bundle.putString("phoneNumber", event.phoneNumber)
                        findNavController().navigate(R.id.action_registerFragment_to_phoneVerificationFragment, bundle)
                    }
                }
            }
        }

        binding.registerButton.setOnClickListener {
            val name = binding.userNameEditText.text.toString()
            val email = binding.userEmailET.text.toString()
            val phoneNumber = binding.userPhoneNumberEditText.text.toString()
            val password = binding.userPasswordET.text.toString()
            viewModel.userClickRegister(
                name = name, phoneNumber = phoneNumber, email = email, password = password
            )
        }
        return binding.root
    }
}