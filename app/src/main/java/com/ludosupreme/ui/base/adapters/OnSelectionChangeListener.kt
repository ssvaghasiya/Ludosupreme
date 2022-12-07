package com.ludosupreme.ui.base.adapters

import androidx.annotation.UiThread


interface OnSelectionChangeListener<T> {

    @UiThread
    fun onSelectionChange(t: T, isSelected: Boolean)
}
