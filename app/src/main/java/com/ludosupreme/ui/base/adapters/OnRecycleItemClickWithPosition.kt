package com.ludosupreme.ui.base.adapters

import android.view.View


interface OnRecycleItemClickWithPosition<T> {

    fun onClick(t: T?, view: View, position: Int)
}
