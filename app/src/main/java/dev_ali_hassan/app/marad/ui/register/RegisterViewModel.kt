package dev_ali_hassan.app.marad.ui.register

import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev_ali_hassan.app.marad.databinding.FragmentLoginBinding
import dev_ali_hassan.app.marad.ui.login.LoginViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor() : ViewModel() {


    private val registerViewModelChannel = Channel<RegisterEvents>()

    // receive the channel as a flow to collect the orders in our login fragment
    val loginViewModelFlow = registerViewModelChannel.receiveAsFlow()


    fun userClickRegister(
        name: String,
        phoneNumber: String,
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            if (name.isBlank())
                registerViewModelChannel.send(RegisterEvents.EmptyName)
            else if (phoneNumber.isBlank())
                registerViewModelChannel.send(RegisterEvents.EmptyPhoneNumber)
            if (password.isBlank())
                registerViewModelChannel.send(RegisterEvents.EmptyPassword)
            else
                registerViewModelChannel.send(RegisterEvents.ShowPhoneVerificationDialog(name, phoneNumber, email, password))
        }
    }
}

sealed class RegisterEvents {
    object EmptyName : RegisterEvents()
    object EmptyPhoneNumber : RegisterEvents()
    object EmptyPassword : RegisterEvents()
    data class ShowPhoneVerificationDialog(
        val name: String,
        val phoneNumber: String,
        val email: String,
        val password: String
    ) : RegisterEvents()

}