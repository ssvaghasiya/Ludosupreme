package com.ludosupreme.ui.manager

import com.ludosupreme.ui.base.BaseActivity
import com.ludosupreme.ui.base.BaseFragment

interface Navigator {

    fun <T : BaseFragment> load(tClass: Class<T>): FragmentActionPerformer<T>

    fun loadActivity(aClass: Class<out BaseActivity>): ActivityBuilder

    fun <T : BaseFragment> loadActivity(aClass: Class<out BaseActivity>, pageTClass: Class<T>): ActivityBuilder

    fun goBack()

    fun finish()

}
