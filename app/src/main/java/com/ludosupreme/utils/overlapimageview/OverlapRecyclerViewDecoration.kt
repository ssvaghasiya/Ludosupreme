package com.ludosupreme.utils.overlapimageview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class OverlapRecyclerViewDecoration(private val overlapLimit: Int, private val overlapWidth: Int) :
    RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {

        //-----get current position of item
        val itemPosition = parent.getChildAdapterPosition(view)

        //-----avoid first item decoration else it will go of the screen
        if (itemPosition == 0) {
            return
        } else {

            //-----apply decoration
            when {
                itemPosition <= overlapLimit -> outRect.set(overlapWidth, 0, 0, 0)

                else -> outRect.set(0, 0, 0, 0)
            }
        }
    }
}