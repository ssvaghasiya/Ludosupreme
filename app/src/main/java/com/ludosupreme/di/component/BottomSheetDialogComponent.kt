package com.ludosupreme.di.component


import com.ludosupreme.di.PerFragment
import com.ludosupreme.di.module.BottomSheetDialogModule
import com.ludosupreme.ui.base.BaseBottomSheetDialog
import dagger.Subcomponent

@PerFragment
@Subcomponent(modules = [(BottomSheetDialogModule::class)])
interface BottomSheetDialogComponent {

    fun baseDialog(): BaseBottomSheetDialog

}
