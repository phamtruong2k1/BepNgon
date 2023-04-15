package com.phamtruong.bepngon.ui.sign.login

import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.phamtruong.bepngon.R
import com.phamtruong.bepngon.databinding.FragmentLoginBinding
import com.phamtruong.bepngon.model.AccountModel
import com.phamtruong.bepngon.ui.main.MainActivity
import com.phamtruong.bepngon.util.Constant
import com.phamtruong.bepngon.sever.FirebaseDatabaseUtil
import com.phamtruong.bepngon.util.showToast
import com.phamtruong.bepngon.view.setOnSafeClick

class LoginFragment : Fragment() {

    companion object {
        const val REQ_ONE_TAP = 1111
    }

    private lateinit var auth: FirebaseAuth
    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest

    lateinit var binding : FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLoginBinding.inflate(inflater, container, false)

        initViewCreated()

        requireContext().showToast("Ok la Log in")

        return binding.root
    }

    private fun initViewCreated() {
        auth = Firebase.auth
        oneTapClient = Identity.getSignInClient(requireActivity())
        signInRequest = getBeginSignInRequest(true)

        initListener()
    }

    private fun getBeginSignInRequest(isFilterByAuthorizedAccount: Boolean = false) =
        BeginSignInRequest.builder()
            .setPasswordRequestOptions(
                BeginSignInRequest.PasswordRequestOptions.builder()
                    .setSupported(true)
                    .build()
            )
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(getString(R.string.default_web_client_id))
                    .setFilterByAuthorizedAccounts(isFilterByAuthorizedAccount)
                    .build()
            )
            .build()

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_ONE_TAP) {
            try {
                val googleCredential = oneTapClient.getSignInCredentialFromIntent(data)
                val idToken = googleCredential.googleIdToken
                when {
                    idToken != null -> {
                        val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                        auth.signInWithCredential(firebaseCredential)
                            .addOnCompleteListener(requireActivity()) { task ->
                                if (task.isSuccessful) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(
                                        Constant.TAG,
                                        "signInWithCredential:success - ${auth.currentUser.toString()}"
                                    )
                                    //showToast("Login success with: ${auth.currentUser?.email}")
                                    if (FirebaseDatabaseUtil.getAccount() != null) {
                                        startActivity(Intent(requireContext(), MainActivity::class.java))
                                        requireActivity().finish()
                                    } else {
                                        FirebaseDatabaseUtil.addNewAccount(
                                            AccountModel(
                                                FirebaseDatabaseUtil.ConvertToMD5(Firebase.auth.currentUser?.email ?: ""),
                                                "",
                                                "",
                                                Firebase.auth.currentUser?.email ?: "",
                                                "",
                                                false
                                            )
                                        )
                                        startActivity(Intent(requireContext(), MainActivity::class.java))
                                        requireActivity().finish()
                                    }
                                } else {
                                    // If sign in fails, display a message to the user.
                                    requireContext().showToast("signInWithCredential:failure")
                                    Log.w(Constant.TAG, "signInWithCredential:failure", task.exception)
//                                    updateUI(null)
                                }
                            }
                    }
                    else -> {
                        // Shouldn't happen.
                        requireContext().showToast("No ID token!")
                        Log.d(Constant.TAG, "No ID token!")
                    }
                }
            } catch (ex: Exception) {
                Log.d(Constant.TAG, "on  result exception: ${ex.printStackTrace()}")
            }

        }
    }

    private fun initListener() {
        binding.llSignIn.setOnSafeClick {
            signInGoogle()
        }

        binding.txtSignUp.setOnSafeClick {
            findNavController().navigate(R.id.action_loginFragment_to_logUpFragment)
        }
    }

    private fun signInGoogle() {
        signInRequest = getBeginSignInRequest()
        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener(requireActivity()) { result ->
                try {
                    startIntentSenderForResult(
                        result.pendingIntent.intentSender, REQ_ONE_TAP,
                        null, 0, 0, 0, null
                    )
                } catch (e: IntentSender.SendIntentException) {
                    Log.e(Constant.TAG, "Couldn't start One Tap UI: ${e.localizedMessage}")
                }
            }
            .addOnFailureListener(requireActivity()) { e ->
                Log.d(Constant.TAG, e.localizedMessage)
            }
    }

}