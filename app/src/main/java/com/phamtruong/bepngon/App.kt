package com.phamtruong.bepngon

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.phamtruong.bepngon.di.AppInjector
import com.facebook.drawee.backends.pipeline.Fresco
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class App : Application() , HasAndroidInjector {

    companion object {
        lateinit var instance: App
    }

    fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)
        Fresco.initialize(this)
        instance = this
    }

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = androidInjector
}