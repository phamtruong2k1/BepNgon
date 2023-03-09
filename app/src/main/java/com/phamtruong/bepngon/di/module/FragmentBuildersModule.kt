package com.phamtruong.bepngon.di.module

import com.phamtruong.bepngon.ui.home.HomeFragment
import com.phamtruong.bepngon.ui.note.NoteFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeNoteFragment(): NoteFragment

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment
}
