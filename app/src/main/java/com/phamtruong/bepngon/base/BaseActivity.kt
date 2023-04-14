package com.phamtruong.bepngon.base

import android.Manifest
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.BuildConfig
import androidx.viewbinding.ViewBinding
import com.phamtruong.bepngon.util.Constant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    private var _binding: VB? = null
    protected lateinit var binding: VB

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        _binding = inflateViewBinding(layoutInflater)
        binding = inflateViewBinding(layoutInflater)
        setContentView(binding.root)
        //lifecycle.addObserver(this)
        initView()
        initData()
        initListener()
        lifecycleScope.launchWhenResumed {
            if (BuildConfig.DEBUG) {
                println("${Constant.TAG} SCREEN_APP ${this@BaseActivity::class.java.name}")
            }
        }
    }

    abstract fun initView()
    abstract fun initData()
    abstract fun initListener()


    /**override it and inflate your view binding, demo in MainActivity*/
    abstract fun inflateViewBinding(inflater: LayoutInflater): VB

    override fun onDestroy() {
        _binding = null
        //lifecycle.removeObserver(this)
        super.onDestroy()
    }

}