package dev_ali_hassan.app.marad.ui.phoneverification

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import dagger.hilt.android.AndroidEntryPoint
import dev_ali_hassan.app.marad.databinding.FragmentPhoneVerificationBinding
import java.util.concurrent.TimeUnit

////AIzaSyA0XZpnhcBI6eao5D7q8fR6FHF5vePjuPE
@AndroidEntryPoint
class PhoneVerificationFragment : Fragment() {

    private lateinit var binding: FragmentPhoneVerificationBinding

    private val TAG: String = "PhoneVerification"

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val callbacks: OnVerificationStateChangedCallbacks =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                Log.d(TAG, "onVerificationCompleted:$credential")
                Log.d(TAG, "onVerificationCompleted: sms code: " + credential.smsCode)
                Toast.makeText(
                    requireContext(),
                    "Done, Code is: ${credential.smsCode}",
                    Toast.LENGTH_LONG
                ).show()
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed: msg: ${e.message}", e)
                Toast.makeText(requireContext(), "Warning: ${e.message}", Toast.LENGTH_LONG).show()
                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    Log.d(TAG, "onVerificationFailed: invalid cred: ${e.message}")
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    Log.d(TAG, "onVerificationFailed: too many requests from your phone!")
                }

                // Show a message and update the UI
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {

                Log.d(TAG, "onCodeSent:$verificationId")

                // Save verification ID and resending token so we can use them later
                /*storedVerificationId = verificationId
                resendToken = token*/
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentPhoneVerificationBinding.inflate(inflater)

        val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
            .setPhoneNumber(
                arguments?.getString("phoneNumber") ?: "+249125350069"
            )       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity())                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
        return binding.root


//    val otpView = findViewById(R.id.otp_view)

    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {

        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")

                    val user = task.result?.user
                    Log.d(TAG, "signInWithPhoneAuthCredential: our user is: ${user.toString()}")
                    Toast.makeText(requireActivity(), "Navigate to home screen", Toast.LENGTH_LONG)
                        .show()
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
            }
    }

}