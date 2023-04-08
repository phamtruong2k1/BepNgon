package com.phamtruong.bepngon.ui.sign

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.phamtruong.bepngon.R
import com.phamtruong.bepngon.base.BaseActivity
import com.phamtruong.bepngon.databinding.ActivitySignBinding
import com.phamtruong.bepngon.databinding.ActivitySplashBinding
import com.phamtruong.bepngon.ui.splash.SplashViewModel

class SignActivity : BaseActivity<SignViewModel, ActivitySignBinding>(SignViewModel::class.java) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_sign
    }

    override fun initViewModel(viewModel: SignViewModel) {}
}