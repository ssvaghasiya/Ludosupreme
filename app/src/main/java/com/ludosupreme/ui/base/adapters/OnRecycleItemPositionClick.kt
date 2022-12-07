package com.ludosupreme.ui.base.adapters

import android.view.View


interface OnRecycleItemPositionClick<T> {

    fun onClick(t: T?, view: View,position:Int)
}
