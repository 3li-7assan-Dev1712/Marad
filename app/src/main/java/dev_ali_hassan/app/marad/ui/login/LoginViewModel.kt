package dev_ali_hassan.app.marad.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev_ali_hassan.app.marad.utilities.AuthenticationManager
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val authenticationManager: AuthenticationManager
) : ViewModel() {


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
                // sign the user in save user authentication state via Jetpack DataStore
                viewModelScope.launch {
                    authenticationManager.updateUserAuthState(userIsAuthenticated = true)
                }
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

    fun checkUserAuthState(userAlreadySignedIn: Boolean) {
        if (userAlreadySignedIn) {
            // navigate to Home Screen Fragment
            viewModelScope.launch {
                loginViewModelChannel.send(LoginEvents.NavigateToHomeScreen)
            }
        }
    }


    sealed class LoginEvents {
        object NavigateToRegisterScreen : LoginEvents()
        object SignIn : LoginViewModel.LoginEvents()
        object NavigateToHomeScreen : LoginEvents()
        class FillEmptyFieldPhoneNumber(val emptyField: String) : LoginEvents()
        class FillEmptyFieldPassword(val emptyField: String) : LoginEvents()
    }
}