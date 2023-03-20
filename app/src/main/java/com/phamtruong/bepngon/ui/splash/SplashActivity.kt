package com.phamtruong.bepngon.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.phamtruong.bepngon.R
import com.phamtruong.bepngon.base.BaseActivity
import com.phamtruong.bepngon.databinding.ActivitySplashBinding
import com.phamtruong.bepngon.ui.main.MainActivity
import kotlinx.coroutines.MainScope

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<SplashViewModel, ActivitySplashBinding>(SplashViewModel::class.java) {
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

    override fun getLayoutRes(): Int {
        return R.layout.activity_splash
    }

    override fun initViewModel(viewModel: SplashViewModel) {}
}