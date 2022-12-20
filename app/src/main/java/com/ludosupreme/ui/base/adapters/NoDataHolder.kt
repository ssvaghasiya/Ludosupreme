package com.ludosupreme.ui.base.adapters

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat


class NoDataHolder<E>(itemView: View) : BaseHolder<E>(itemView) {
    var errorTextView: TextView? = null

    init {
        errorTextView = itemView as TextView
    }

    fun setErrorText(errorText: String) {
        if (errorTextView != null) {
            errorTextView!!.text = errorText
        }
    }
}