package com.ludosupreme.ui.home.adapter

import android.os.CountDownTimer
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.ludosupreme.R
import com.ludosupreme.databinding.ThirdItemBinding
import com.ludosupreme.extenstions.viewGone
import com.ludosupreme.extenstions.viewShow
import com.ludosupreme.ui.base.adapters.AdvanceRecycleViewAdapter
import com.ludosupreme.ui.base.adapters.BaseHolder
import com.ludosupreme.ui.base.adapters.OnRecycleItemClickWithPosition
import com.ludosupreme.ui.home.model.TournamentsData
import java.util.*
import java.util.concurrent.TimeUnit


class HomeTournamentsAdapter(var onRecycleItemClickWithPosition: OnRecycleItemClickWithPosition<TournamentsData>) :
    AdvanceRecycleViewAdapter<HomeTournamentsAdapter.ViewHolder, TournamentsData>() {

    var timeMillis: Long = 120000

    inner class ViewHolder(view: View) : BaseHolder<TournamentsData>(view) {
        private val viewBinding = ThirdItemBinding.bind(view)
        var timer: CountDownTimer? = null

        fun bindData(holder: ViewHolder, item: TournamentsData) = with(viewBinding) {
            item.apply {
                if (!isFinished) {
                    if (timer != null) {
                        timer?.cancel()
                    }
                    timer = object : CountDownTimer(timeMillis, 1000) {
                        override fun onTick(millisUntilFinished: Long) {
                            timeMillis = millisUntilFinished
                            val minutes: Long =
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                    TimeUnit.MILLISECONDS.toHours(millisUntilFinished)
                                )
                            val seconds: Long =
                                TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                                )

                            textViewLabelTime.text = String.format("%2dM : %2dS", minutes, seconds)
                        }

                        override fun onFinish() {
                            timer?.cancel()
                            isFinished = true
                            timer = null
//                            notifyItemChanged(adapterPosition)
                        }
                    }.start()
                }

                if (userCount == 0) {
                    val i1: Int = Random().nextInt(500 - 1) + 28
                    userCount = i1
                }
                textViewLabelCount.text = userCount.toString()
                textViewLabelPlayer.text = player
                textViewPrizePool.text = prizePool
                textViewPriceEntry.text = entry

                imageViewDropDown.setOnClickListener {
                    constraintLayoutPrizePoolData.isSelected =
                        !constraintLayoutPrizePoolData.isSelected
                    if (constraintLayoutPrizePoolData.isSelected) {
                        val lparam =
                            constraintLayoutParent.layoutParams as ConstraintLayout.LayoutParams
                        lparam.matchConstraintPercentHeight =
                            root.context.resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._320sdp)
                                .toFloat()
                        constraintLayoutParent.layoutParams = lparam
                        constraintLayoutPrizePoolData.viewShow()
                    } else {
                        val lparam =
                            constraintLayoutParent.layoutParams as ConstraintLayout.LayoutParams
                        lparam.matchConstraintPercentHeight =
                            root.context.resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._150sdp)
                                .toFloat()
                        constraintLayoutParent.layoutParams = lparam
                        constraintLayoutPrizePoolData.viewGone()
                    }
                }
                /*if (isFinished) {
                    imageViewRupee.viewGone()
                    textViewPriceEntry.text = "Join"
                } else {
                    imageViewRupee.viewShow()
                    textViewPriceEntry.text = "5"
                }*/
            }
        }
    }


    override fun createDataHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(makeView(parent, R.layout.third_item))
    }

    override fun onBindDataHolder(
        holder: ViewHolder,
        position: Int,
        item: TournamentsData
    ) {
        holder.bindData(holder, item)
    }


}