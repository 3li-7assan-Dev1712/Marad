package dev_ali_hassan.app.marad.ui.launcherscreen



import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import dev_ali_hassan.app.marad.ui.MainActivity

@AndroidEntryPoint
class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // this intent will lead to the main activity
        val intent_to_main_activty = Intent(this, MainActivity::class.java)
        startActivity(intent_to_main_activty)
        finish()
    }
}