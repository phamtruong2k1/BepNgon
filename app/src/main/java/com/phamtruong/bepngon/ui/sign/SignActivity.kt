package com.phamtruong.bepngon.ui.sign

import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.phamtruong.bepngon.R
import com.phamtruong.bepngon.base.BaseActivity
import com.phamtruong.bepngon.databinding.ActivityMainBinding
import com.phamtruong.bepngon.databinding.ActivitySignBinding
import com.phamtruong.bepngon.util.Constant
import com.phamtruong.bepngon.util.FirebaseDatabaseUtil
import com.phamtruong.bepngon.util.showToast
import com.phamtruong.bepngon.view.setOnSafeClick

class SignActivity : BaseActivity<ActivitySignBinding>() {


    companion object {
        const val REQ_ONE_TAP = 1111
    }

    private lateinit var auth: FirebaseAuth
    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest


    override fun initView() {

    }

    override fun initData() {
        auth = Firebase.auth
        oneTapClient = Identity.getSignInClient(this)
        signInRequest = getBeginSignInRequest(true)
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
                        // Got an ID token from Google. Use it to authenticate
                        // with Firebase.
                        val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                        auth.signInWithCredential(firebaseCredential)
                            .addOnCompleteListener(this) { task ->
                                if (task.isSuccessful) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(
                                        Constant.TAG,
                                        "signInWithCredential:success - ${auth.currentUser.toString()}"
                                    )
                                    showToast("Login success with: ${auth.currentUser?.email}")
//                                    updateUI(auth.currentUser)
                                } else {
                                    // If sign in fails, display a message to the user.
                                    showToast("signInWithCredential:failure")
                                    Log.w(Constant.TAG, "signInWithCredential:failure", task.exception)
//                                    updateUI(null)
                                }
                            }
                    }
                    else -> {
                        // Shouldn't happen.
                        showToast("No ID token!")
                        Log.d(Constant.TAG, "No ID token!")
                    }
                }
            } catch (ex: Exception) {
                Log.d(Constant.TAG, "on  result exception: ${ex.printStackTrace()}")
            }

        }
    }

    override fun initListener() {
        binding.llSignIn.setOnSafeClick {
            signInGoogle()
        }
    }

    private fun signInGoogle() {
        signInRequest = getBeginSignInRequest()
        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener(this) { result ->
                try {
                    startIntentSenderForResult(
                        result.pendingIntent.intentSender, REQ_ONE_TAP,
                        null, 0, 0, 0, null
                    )
                } catch (e: IntentSender.SendIntentException) {
                    Log.e(Constant.TAG, "Couldn't start One Tap UI: ${e.localizedMessage}")
                }
            }
            .addOnFailureListener(this) { e ->
                Log.d(Constant.TAG, e.localizedMessage)
            }
    }

    override fun inflateViewBinding(inflater: LayoutInflater): ActivitySignBinding {
        return ActivitySignBinding.inflate(inflater)
    }
}