package com.phamtruong.bepngon.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.phamtruong.bepngon.R
import com.phamtruong.bepngon.base.BaseActivity
import com.phamtruong.bepngon.databinding.ActivitySplashBinding
import com.phamtruong.bepngon.ui.main.MainActivity
import com.phamtruong.bepngon.ui.sign.SignActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        checkLogin()
    }

    private fun checkLogin() : Boolean {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            startMain(1500L)
        } else {
            startLogin(1500L)
        }
        return false
    }

    private fun startMain(timeDelay : Long = 0L) {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }, timeDelay)
    }

    private fun startLogin(timeDelay : Long = 0L) {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@SplashActivity, SignActivity::class.java))
            finish()
        }, timeDelay)
    }

    override fun initView() {

    }

    override fun initData() {

    }

    override fun initListener() {

    }



    override fun inflateViewBinding(inflater: LayoutInflater): ActivitySplashBinding {
        return ActivitySplashBinding.inflate(inflater)
    }
}