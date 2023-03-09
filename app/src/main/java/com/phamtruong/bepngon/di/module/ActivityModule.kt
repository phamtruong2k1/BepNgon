package com.phamtruong.bepngon.di.module


import com.phamtruong.bepngon.di.scope.PerActivity
import com.phamtruong.bepngon.ui.main.MainActivity
import com.phamtruong.bepngon.ui.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @PerActivity
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    internal abstract fun mainActivity(): MainActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    internal abstract fun splashActivity(): SplashActivity

}
