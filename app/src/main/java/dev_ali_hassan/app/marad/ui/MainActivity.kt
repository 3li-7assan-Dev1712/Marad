package dev_ali_hassan.app.marad.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev_ali_hassan.app.marad.R
import dev_ali_hassan.app.marad.utilities.AuthenticationManager
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var navController: NavController

    @Inject
    lateinit var authenticationManager: AuthenticationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navController = navHostFragment.findNavController()


        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.nav_graph)

//        Log.d(TAG, "onCreate: 1712 == " + authenticationManager.authenticationFlow.colle )
/*        if (authenticationManager.userAuthLiveData.value == true) {
            graph.setStartDestination(R.id.homeFragment)
        } else {
            graph.setStartDestination(R.id.loginFragment)
        }*/
//        navHostFragment.navController.graph = graph

        val it = applicationContext.getSharedPreferences(
            "auth",
            Context.MODE_PRIVATE
        ).getBoolean("isSignIn", false)

        /* lifecycleScope.launchWhenCreated {
             authenticationManager.authenticationFlow.collect {*/
        if (it) {
            Log.d(TAG, "onCreate: navigating to home screen")
            graph.setStartDestination(R.id.homeFragment)
            navHostFragment.navController.graph = graph
            Toast.makeText(applicationContext, "Navigating", Toast.LENGTH_LONG).show()
        } else {
            Log.d(TAG, "onCreate: navigating to login screen")
            graph.setStartDestination(R.id.loginFragment)
            navHostFragment.navController.graph = graph
            Toast.makeText(applicationContext, "Navigating", Toast.LENGTH_LONG).show()
            /* }
//                navHostFragment.navController.graph = graph
         }*/
        }
        navHostFragment.navController.graph = graph

    }

    override fun onSupportNavigateUp(): Boolean {
        Log.d("MainActivity", "onSupportNavigateUp: navigating up")
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}