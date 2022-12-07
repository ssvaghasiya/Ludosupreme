package com.ludosupreme.utils.overlapimageview


interface OverlapRecyclerViewClickListener {
    fun onNormalItemClicked(adapterPosition: Int)

    fun onNumberedItemClick(adapterPosition: Int)
}