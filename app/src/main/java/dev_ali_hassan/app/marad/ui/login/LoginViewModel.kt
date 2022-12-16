package dev_ali_hassan.app.marad.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {


   private val  loginViewModelChannel = Channel<LoginEvents>()

    // receive the channel as a flow to collect the orders in our login fragment
    val loginViewModelFlow = loginViewModelChannel.receiveAsFlow()


    fun loginButtonClick(phoneNumber: String, password: String) {
        viewModelScope.launch {
            if (phoneNumber.isBlank())
                loginViewModelChannel.send(LoginEvents.FillEmptyFieldPhoneNumber("please, enter your phone number"))
            else if (password.isBlank())
                loginViewModelChannel.send(LoginEvents.FillEmptyFieldPassword("please, enter your password"))
            else {
                // saved that the user is authenticated
                // sign the user in
                // navigate to home screen fragment
                loginViewModelChannel.send(LoginEvents.SignIn)
            }
        }


    }

    fun userClickRegister() {
        viewModelScope.launch {
            loginViewModelChannel.send(LoginEvents.NavigateToRegisterScreen)
        }
    }

    sealed class LoginEvents {
        object NavigateToRegisterScreen : LoginEvents()
        object SignIn : LoginViewModel.LoginEvents()

        class FillEmptyFieldPhoneNumber(val emptyField: String) : LoginEvents()
        class FillEmptyFieldPassword(val emptyField: String) : LoginEvents()
    }
}