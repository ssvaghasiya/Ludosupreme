package com.ludosupreme.ui.home.adapter

import android.os.CountDownTimer
import android.view.View
import android.view.ViewGroup
import com.ludosupreme.R
import com.ludosupreme.databinding.ThirdItemBinding
import com.ludosupreme.extenstions.viewGone
import com.ludosupreme.extenstions.viewShow
import com.ludosupreme.ui.base.adapters.AdvanceRecycleViewAdapter
import com.ludosupreme.ui.base.adapters.BaseHolder
import com.ludosupreme.ui.base.adapters.OnRecycleItemClickWithPosition
import com.ludosupreme.ui.home.model.TournamentsData
import java.text.MessageFormat
import java.util.concurrent.TimeUnit


class HomeTournamentsAdapter(var onRecycleItemClickWithPosition: OnRecycleItemClickWithPosition<TournamentsData>) :
    AdvanceRecycleViewAdapter<HomeTournamentsAdapter.ViewHolder, TournamentsData>() {

    var timeMillis: Long = 120000

    inner class ViewHolder(view: View) : BaseHolder<TournamentsData>(view) {
        private val viewBinding = ThirdItemBinding.bind(view)
        var timer: CountDownTimer? = null

        fun bindData(holder: ViewHolder, item: TournamentsData) = with(viewBinding) {
            item.apply {
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
                        isFinished = true
                    }
                }.start()
                if (isFinished) {
                    imageViewRupee.viewGone()
                    textViewPriceEntry.text = "Join"
                } else {
                    imageViewRupee.viewShow()
                    textViewPriceEntry.text = "5"
                }
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