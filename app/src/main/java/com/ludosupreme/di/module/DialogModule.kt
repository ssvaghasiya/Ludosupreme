package com.ludosupreme.di.module


import com.ludosupreme.di.PerFragment
import com.ludosupreme.ui.base.BaseDialog
import dagger.Module
import dagger.Provides

@Module
class DialogModule(private val baseDialog: BaseDialog) {

    @Provides
    @PerFragment
    internal fun provideBaseDialog(): BaseDialog {
        return baseDialog
    }

}
