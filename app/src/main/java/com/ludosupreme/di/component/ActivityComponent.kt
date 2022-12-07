package com.ludosupreme.di.component


import com.ludosupreme.ui.authentication.activity.AuthenticationActivity
import com.ludosupreme.di.PerActivity
import com.ludosupreme.di.module.ActivityModule
import com.ludosupreme.di.module.BottomSheetDialogModule
import com.ludosupreme.di.module.DialogModule
import com.ludosupreme.di.module.FragmentModule
import com.ludosupreme.ui.home.activity.HomeActivity
import com.ludosupreme.ui.home.activity.IsolatedActivity
import com.ludosupreme.ui.base.BaseActivity
import com.ludosupreme.ui.manager.Navigator

import dagger.BindsInstance
import dagger.Component

@PerActivity
@Component(dependencies = [ApplicationComponent::class], modules = arrayOf(ActivityModule::class))
interface ActivityComponent {

    fun activity(): BaseActivity

    fun navigator(): Navigator


    operator fun plus(fragmentModule: FragmentModule): FragmentComponent
    operator fun plus(dialogModule: DialogModule): DialogComponent
    operator fun plus(bottomSheetDialogModule: BottomSheetDialogModule): BottomSheetDialogComponent

    fun inject(authenticationActivity: AuthenticationActivity)
    fun inject(homeActivity: HomeActivity)
    fun inject(isolatedActivity: IsolatedActivity)

    @Component.Builder
    interface Builder {

        fun bindApplicationComponent(applicationComponent: ApplicationComponent): Builder

        @BindsInstance
        fun bindActivity(baseActivity: BaseActivity): Builder

        fun build(): ActivityComponent
    }
}
