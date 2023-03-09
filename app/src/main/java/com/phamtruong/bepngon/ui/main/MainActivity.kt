package com.phamtruong.bepngon.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.phamtruong.bepngon.R
import com.phamtruong.bepngon.base.BaseActivity
import com.phamtruong.bepngon.databinding.ActivityMainBinding

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(MainViewModel::class.java) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_main
    }

    override fun initViewModel(viewModel: MainViewModel) {}
}