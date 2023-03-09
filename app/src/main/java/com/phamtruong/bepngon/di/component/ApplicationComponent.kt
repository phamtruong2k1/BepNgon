package com.phamtruong.bepngon.di.component

import android.app.Application
import com.phamtruong.bepngon.App
import com.phamtruong.bepngon.di.module.ActivityModule
import com.phamtruong.bepngon.di.module.ApplicationModule
import com.phamtruong.bepngon.di.module.DatabaseModule
import com.phamtruong.bepngon.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ApplicationModule::class,
        ActivityModule::class,
        DatabaseModule::class,
        ViewModelModule::class
    ]
)

interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)
}
