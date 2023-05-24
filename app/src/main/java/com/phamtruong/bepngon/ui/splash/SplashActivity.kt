package com.phamtruong.bepngon.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.ktx.getValue
import com.phamtruong.bepngon.databinding.ActivitySplashBinding
import com.phamtruong.bepngon.model.AccountModel
import com.phamtruong.bepngon.sever.AccountFBUtil
import com.phamtruong.bepngon.ui.admin.MainAdminActivity
import com.phamtruong.bepngon.ui.user.main.MainActivity
import com.phamtruong.bepngon.ui.sign.SignActivity
import com.phamtruong.bepngon.util.SharePreferenceUtils

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding

    companion object {
        const val ACCOUNT = "account"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkLogin()
    }

    private fun checkLogin() {
        val id = SharePreferenceUtils.getAccountID()
        AccountFBUtil.mDatabase.child(ACCOUNT).child(id).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val result = task.result
                    val account = result.getValue<AccountModel>()
                    if (account != null) {
                        if (account.password == SharePreferenceUtils.getPassword()
                            && account.userName == SharePreferenceUtils.getUserName()
                        ) {
                            SharePreferenceUtils.setAccountID(account.account_id)
                            if (account.role == "admin") {
                                startAdminMain()
                            } else {
                                startMain()
                            }
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

    private fun startMain(timeDelay: Long = 500L) {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }, timeDelay)
    }

    private fun startAdminMain(timeDelay: Long = 500L) {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@SplashActivity, MainAdminActivity::class.java))
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