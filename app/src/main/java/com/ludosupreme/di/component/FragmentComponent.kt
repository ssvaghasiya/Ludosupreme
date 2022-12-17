package com.ludosupreme.di.component


import com.ludosupreme.di.PerFragment
import com.ludosupreme.di.module.FragmentModule
import com.ludosupreme.ui.authentication.fragment.LoginFragment
import com.ludosupreme.ui.base.BaseFragment
import com.ludosupreme.ui.home.fragment.SecondFragment
import com.ludosupreme.ui.home.fragment.ThirdFragment
import com.ludosupreme.ui.splash.SplashFragment
import dagger.Subcomponent

@PerFragment
@Subcomponent(modules = [(FragmentModule::class)])
interface FragmentComponent {
    fun baseFragment(): BaseFragment
    fun inject(splashFragment: SplashFragment)
    fun inject(loginFragment: LoginFragment)
    fun inject(secondFragment: SecondFragment)
    fun inject(thirdFragment: ThirdFragment)

}
