package com.ludosupreme.ui.home.adapter

import android.view.View
import android.view.ViewGroup
import com.ludosupreme.R
import com.ludosupreme.databinding.SecondItemBinding
import com.ludosupreme.databinding.TransactionHistoryItemBinding
import com.ludosupreme.ui.base.adapters.AdvanceRecycleViewAdapter
import com.ludosupreme.ui.base.adapters.BaseHolder
import com.ludosupreme.ui.base.adapters.OnRecycleItemClickWithPosition
import com.ludosupreme.ui.home.model.TransactionHistoryData


class TransactionHistoryAdapter(var onRecycleItemClickWithPosition: OnRecycleItemClickWithPosition<TransactionHistoryData>) :
    AdvanceRecycleViewAdapter<TransactionHistoryAdapter.ViewHolder, TransactionHistoryData>() {

    inner class ViewHolder(view: View) : BaseHolder<TransactionHistoryData>(view) {
        private val viewBinding = TransactionHistoryItemBinding.bind(view)
        fun bindData(item: TransactionHistoryData) = with(viewBinding) {
            item.apply {

            }
        }
    }


    override fun createDataHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(makeView(parent, R.layout.transaction_history_item))
    }

    override fun onBindDataHolder(
        holder: ViewHolder,
        position: Int,
        item: TransactionHistoryData
    ) {
        holder.bindData(item)
    }


}