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
        val relativeLayout = itemView as RelativeLayout
        val layoutParams = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        relativeLayout.layoutParams = layoutParams
        relativeLayout.gravity = Gravity.CENTER_HORIZONTAL
        errorTextView = TextView(itemView.getContext())
        val params = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(10, 80, 10, 80)
        errorTextView!!.layoutParams = params
        //errorTextView.setTypeface(ResourcesCompat.getFont(itemView.getContext(), R.font.robotomedium));
        //errorTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, itemView.getContext().getResources().getDimension(R.dimen._14ssp));
        relativeLayout.addView(errorTextView)
    }


    fun setErrorText(errorText: String?) {
        if (errorTextView != null) {
            if (errorText != null) errorTextView!!.text = errorText else errorTextView!!.text =
                "No data available"
        }
    }

    fun setTextColor(@ColorRes color: Int) {
        if (errorTextView != null) {
            errorTextView!!.setTextColor(ContextCompat.getColor(errorTextView!!.context, color))
        }
    }
}