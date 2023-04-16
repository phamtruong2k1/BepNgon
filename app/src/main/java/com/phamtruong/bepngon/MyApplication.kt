package com.phamtruong.bepngon

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import com.phamtruong.bepngon.util.SharePreferenceUtils

class MyApplication : Application()  {

    override fun onCreate() {
        super.onCreate()
        SharePreferenceUtils.init(this)
        Fresco.initialize(this)
    }
}