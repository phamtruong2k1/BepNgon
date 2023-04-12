package com.phamtruong.bepngon.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import com.phamtruong.bepngon.R
import com.phamtruong.bepngon.base.BaseActivity
import com.phamtruong.bepngon.databinding.ActivitySplashBinding
import com.phamtruong.bepngon.ui.main.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startMain(1500L)
    }

    private fun startMain(timeDelay : Long = 0L) {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
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