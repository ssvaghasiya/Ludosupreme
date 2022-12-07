package com.ludosupreme.ui.base.adapters


interface SelectionActionPerformer<out E : AbstractSelectableAdapter.Selectable> {

    @get:AbstractSelectableAdapter.SelectionMode
    val selectionMode: Int

    fun getItem(index: Int): E

    fun select(index: Int)

    fun deSelect(index: Int)
}
