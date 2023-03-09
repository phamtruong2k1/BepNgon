package com.phamtruong.bepngon.ui.splash

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.phamtruong.bepngon.R
import com.phamtruong.bepngon.base.BaseActivity
import com.phamtruong.bepngon.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<SplashViewModel, ActivitySplashBinding>(SplashViewModel::class.java) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_splash
    }

    override fun initViewModel(viewModel: SplashViewModel) {}
}