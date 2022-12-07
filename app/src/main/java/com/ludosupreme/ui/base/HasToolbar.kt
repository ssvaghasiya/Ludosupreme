package com.ludosupreme.ui.base

import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.appcompat.widget.Toolbar

interface HasToolbar {

    fun setToolbar(toolbar: Toolbar)

    fun showToolbar(b: Boolean)

    fun showToolbarIcon(b: Boolean)

    fun setToolbarTitle(title: String)

    fun showBackButton(b: Boolean)

    fun showImageViewGroupCircle(b: Boolean)

    fun setToolbarColor(color: Int)

    fun setCoordinatorLayoutColor(color: Int)

    fun toolbarTransparent(b: Boolean)

}
