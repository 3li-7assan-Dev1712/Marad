package dev_ali_hassan.app.marad.ui.launcherscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev_ali_hassan.app.marad.R
import dev_ali_hassan.app.marad.ui.homescreen.HomeScreenViewModel
import dev_ali_hassan.app.marad.ui.login.LoginViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LauncherFragment: Fragment(R.layout.fragment_launcher) {

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            viewModel.authenticationManager.authenticationFlow.collect {
                if (it) {
                    findNavController().navigate(R.id.action_launcherFragment_to_homeFragment)
                } else {
                    findNavController().navigate(R.id.action_launcherFragment_to_loginFragment)
                }
            }
        }
    }
}