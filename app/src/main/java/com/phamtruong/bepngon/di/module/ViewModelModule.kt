package com.phamtruong.bepngon.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.phamtruong.bepngon.di.ViewModelFactory
import com.phamtruong.bepngon.di.key.ViewModelKey
import com.phamtruong.bepngon.ui.home.HomeViewModel
import com.phamtruong.bepngon.ui.main.MainViewModel
import com.phamtruong.bepngon.ui.note.NoteViewModel
import com.phamtruong.bepngon.ui.splash.SplashActivity
import com.phamtruong.bepngon.ui.splash.SplashViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @IntoMap
    @Binds
    @ViewModelKey(MainViewModel::class)
    abstract fun provideMainViewModel(mainViewModel: MainViewModel): ViewModel

    @IntoMap
    @Binds
    @ViewModelKey(SplashViewModel::class)
    abstract fun provideSplashViewModel(splashViewModel: SplashViewModel): ViewModel

    @IntoMap
    @Binds
    @ViewModelKey(NoteViewModel::class)
    abstract fun provideNoteViewModel(noteViewModel: NoteViewModel): ViewModel

    @IntoMap
    @Binds
    @ViewModelKey(HomeViewModel::class)
    abstract fun provideHomeViewModel(homeViewModel: HomeViewModel): ViewModel


}
