package dev_ali_hassan.app.marad.ui.phoneverification


import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject


@HiltViewModel
class PhoneVerificationViewMode @Inject constructor(): ViewModel() {

    private val  phoneVerificationViewModelChannel = Channel<Events>()

    // receive the channel as a flow to collect the orders in our login fragment
    val loginViewModelFlow = phoneVerificationViewModelChannel.receiveAsFlow()


}

sealed class Events {

}