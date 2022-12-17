package com.ludosupreme.ui.home.adapter

import android.view.View
import android.view.ViewGroup
import com.ludosupreme.R
import com.ludosupreme.databinding.SecondItemBinding
import com.ludosupreme.databinding.ThirdItemBinding
import com.ludosupreme.ui.base.adapters.AdvanceRecycleViewAdapter
import com.ludosupreme.ui.base.adapters.BaseHolder
import com.ludosupreme.ui.base.adapters.OnRecycleItemClickWithPosition


class HomeTournamentsAdapter(var onRecycleItemClickWithPosition: OnRecycleItemClickWithPosition<String>) :
    AdvanceRecycleViewAdapter<HomeTournamentsAdapter.ViewHolder, String>() {

    inner class ViewHolder(view: View) : BaseHolder<String>(view) {
        private val viewBinding = ThirdItemBinding.bind(view)
        fun bindData(item: String) = with(viewBinding) {
            item.apply {

            }
        }
    }


    override fun createDataHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(makeView(parent, R.layout.third_item))
    }

    override fun onBindDataHolder(
        holder: ViewHolder,
        position: Int,
        item: String
    ) {
        holder.bindData(item)
    }


}