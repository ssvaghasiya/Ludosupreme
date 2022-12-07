package com.ludosupreme.ui.base.adapters

import android.view.View


interface OnRecycleItemClick<T> {

    fun onClick(t: T?, view: View)
}
