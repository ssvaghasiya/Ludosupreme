package com.ludosupreme.di.component


import com.ludosupreme.di.PerFragment
import com.ludosupreme.di.module.DialogModule
import com.ludosupreme.ui.base.BaseDialog
import com.ludosupreme.utils.customLoader.CustomLoaderDialog
import dagger.Subcomponent

@PerFragment
@Subcomponent(modules = [(DialogModule::class)])
interface DialogComponent {

    fun baseDialog(): BaseDialog
    fun inject(customLoaderDialog: CustomLoaderDialog)

}
