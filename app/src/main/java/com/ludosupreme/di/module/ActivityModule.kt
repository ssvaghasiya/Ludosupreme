package com.ludosupreme.di.module

import com.ludosupreme.di.PerActivity
import com.ludosupreme.ui.base.BaseActivity
import com.ludosupreme.ui.manager.FragmentHandler
import com.ludosupreme.ui.manager.Navigator

import javax.inject.Named

import dagger.Module
import dagger.Provides

@Module
class ActivityModule {

    @Provides
    @PerActivity
    internal fun navigator(activity: BaseActivity): Navigator {
        return activity
    }

    @Provides
    @PerActivity
    internal fun fragmentManager(baseActivity: BaseActivity): androidx.fragment.app.FragmentManager {
        return baseActivity.supportFragmentManager
    }

    @Provides
    @PerActivity
    @Named("placeholder")
    internal fun placeHolder(baseActivity: BaseActivity): Int {
        return baseActivity.findFragmentPlaceHolder()
    }

    @Provides
    @PerActivity
    internal fun fragmentHandler(fragmentManager: com.ludosupreme.ui.manager.FragmentManager): FragmentHandler {
        return fragmentManager
    }


}
