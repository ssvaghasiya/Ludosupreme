package com.ludosupreme.di.module

import com.ludosupreme.di.PerFragment
import com.ludosupreme.ui.base.BaseFragment
import dagger.Module
import dagger.Provides

@Module
class FragmentModule(private val baseFragment: BaseFragment) {

    @Provides
    @PerFragment
    internal fun provideBaseFragment(): BaseFragment {
        return baseFragment
    }

}
