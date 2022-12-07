package com.ludosupreme.di.module


import com.ludosupreme.di.PerFragment
import com.ludosupreme.ui.base.BaseBottomSheetDialog
import dagger.Module
import dagger.Provides

@Module
class BottomSheetDialogModule(private val baseBottomSheetDialog: BaseBottomSheetDialog) {

    @Provides
    @PerFragment
    internal fun provideBaseBottomSheetDialog(): BaseBottomSheetDialog {
        return baseBottomSheetDialog
    }

}
