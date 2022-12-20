package com.ludosupreme.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ludosupreme.R
import com.ludosupreme.databinding.SecondItemBinding
import com.ludosupreme.databinding.TransactionHistoryItemBinding
import com.ludosupreme.ui.base.adapters.AdvanceRecycleViewAdapter
import com.ludosupreme.ui.base.adapters.BaseHolder
import com.ludosupreme.ui.base.adapters.OnRecycleItemClickWithPosition

class TransactionHistoryTempAdapter : ListAdapter<String, RecyclerView.ViewHolder>(FeatureDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.transaction_history_item, parent, false)
        return FeatureItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemViewHolder = holder as FeatureItemViewHolder
        itemViewHolder.bind(getItem(position))
    }

    inner class FeatureItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val viewBinding = TransactionHistoryItemBinding.bind(view)
        fun bind(feature: String) {
            with(viewBinding) {
            }
        }
    }
}

class FeatureDiffCallback : DiffUtil.ItemCallback<String>() {

    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
        oldItem == newItem
}