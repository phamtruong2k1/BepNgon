package com.phamtruong.bepngon.ui.splash

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.phamtruong.bepngon.R
import com.phamtruong.bepngon.base.BaseActivity
import com.phamtruong.bepngon.databinding.ActivitySignBinding
import com.phamtruong.bepngon.databinding.ActivitySplashBinding
import com.phamtruong.bepngon.model.AccountModel
import com.phamtruong.bepngon.sever.account.AccountFirebaseUtil
import com.phamtruong.bepngon.ui.main.MainActivity
import com.phamtruong.bepngon.ui.sign.SignActivity
import com.phamtruong.bepngon.util.SharePreferenceUtils
import com.phamtruong.bepngon.util.convertToMD5
import com.phamtruong.bepngon.util.showToast

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding
    companion object{
        const val ACCOUNT = "account"
    }

    private lateinit var navController: NavController
    private var navHostFragment: NavHostFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkLogin()
    }

    private fun checkLogin() {

        val id = SharePreferenceUtils.getAccountID()

        AccountFirebaseUtil.mDatabase.child(ACCOUNT).child(id).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val result = task.result
                    val account = result.getValue<AccountModel>()
                    if (account != null) {
                        if (account.password == SharePreferenceUtils.getPassword()
                            && account.userName == SharePreferenceUtils.getUserName()
                        ) {
                            SharePreferenceUtils.setAccountID(account.account_id)
                            startMain()
                        } else {
                            startLogin()
                        }
                    } else {
                        startLogin()
                    }
                }
            }.addOnFailureListener {
                startLogin()
        }
    }

    private fun startMain(timeDelay: Long = 1000L) {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }, timeDelay)
    }

    private fun startLogin(timeDelay: Long = 1000L) {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@SplashActivity, SignActivity::class.java))
            finish()
        }, timeDelay)
    }

    override fun onBackPressed() {}
}