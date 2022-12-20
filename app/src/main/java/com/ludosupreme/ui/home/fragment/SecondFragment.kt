package com.ludosupreme.ui.home.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.*
import com.ludosupreme.R
import com.ludosupreme.databinding.SecondFragmentBinding
import com.ludosupreme.di.component.FragmentComponent
import com.ludosupreme.ui.base.BaseFragment
import com.ludosupreme.ui.base.adapters.OnRecycleItemClickWithPosition
import com.ludosupreme.ui.home.adapter.HomeAdsAdapter
import com.ludosupreme.ui.home.adapter.TransactionHistoryAdapter
import com.ludosupreme.ui.home.adapter.TransactionHistoryTempAdapter
import com.ludosupreme.ui.home.model.TransactionHistoryData
import com.ludosupreme.utils.CirclePagerIndicatorDecoration
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SecondFragment : BaseFragment(), View.OnClickListener {

    private var _binding: SecondFragmentBinding? = null
    private val binding: SecondFragmentBinding
        get() = _binding!!

    lateinit var adsAdapter: HomeAdsAdapter
    lateinit var transactionHistoryAdapter: TransactionHistoryAdapter
    private var handler: Handler? = null
    private var runnable: Runnable? = null
    private val dx = 10 // used for speed
    private val durationInMS = 50 // ms

    override fun createLayout(): Int = R.layout.second_fragment

    override fun bindViewWithViewBinding(view: View) {
        _binding = SecondFragmentBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun destroyViewBinding() {
        _binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun bindData() {
        initView()
        setOnClickListener()
    }


    private fun initView() = with(binding) {
        setAdsAdapter()
        setTransactionHistoryAdapter()
    }


    private fun setAdsAdapter() = with(binding) {
        adsAdapter =
            HomeAdsAdapter(
                object : OnRecycleItemClickWithPosition<String> {
                    override fun onClick(t: String?, view: View, position: Int) {
                        when (view.id) {
                            R.id.buttonPlayNow -> {
                                navigator.load(ThirdFragment::class.java).replace(true)
                            }
                        }
                    }
                })
        recyclerViewLudo.adapter = adsAdapter
        adsAdapter.items = ArrayList()

        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(recyclerViewLudo)

        val circlePagerIndicatorDecoration = CirclePagerIndicatorDecoration()
        circlePagerIndicatorDecoration.setDotColors(
            ContextCompat.getColor(
                requireContext(),
                R.color.white
            ), ContextCompat.getColor(requireContext(), R.color.colorAccent)
        )
//        recyclerViewLudo.addItemDecoration(circlePagerIndicatorDecoration)

        adsAdapter.items?.add("")
        adsAdapter.notifyDataSetChanged()
    }

    private fun setTransactionHistoryAdapter() = with(binding) {
        handler = Handler(Looper.getMainLooper())
        runnable = object : Runnable {
            override fun run() {
                if (recyclerViewTransaction != null && handler != null) {
                    recyclerViewTransaction.smoothScrollBy(dx, 0)
                    handler!!.postDelayed(this, durationInMS.toLong())
                }
            }
        }
        if (handler != null && runnable != null) {
            handler!!.postDelayed(runnable!!, 500)
        }

        transactionHistoryAdapter =
            TransactionHistoryAdapter(object : OnRecycleItemClickWithPosition<TransactionHistoryData> {
                override fun onClick(t: TransactionHistoryData?, view: View, position: Int) {
                    when (view.id) {
                        R.id.buttonPlayNow -> {
                            navigator.load(ThirdFragment::class.java).replace(true)
                        }
                    }
                }
            })
        val mLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, true)

        recyclerViewTransaction.layoutManager = mLayoutManager
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(object : Runnable {
            override fun run() {
                handler.postDelayed(this, 100)
            }
        }, 1000)

        recyclerViewTransaction.adapter = transactionHistoryAdapter
        transactionHistoryAdapter.items = ArrayList()

        transactionHistoryAdapter.items?.add(TransactionHistoryData("Hemil****@paytm","2000","19 min ago"))
        transactionHistoryAdapter.items?.add(TransactionHistoryData("James****@paytm","5000","30 min ago"))
        transactionHistoryAdapter.items?.add(TransactionHistoryData("Robert****@paytm","1000","1 hr ago"))
        transactionHistoryAdapter.items?.add(TransactionHistoryData("John****@paytm","3000","19 hr ago"))
        transactionHistoryAdapter.items?.add(TransactionHistoryData("Michael****@paytm","4000","22 min ago"))

        transactionHistoryAdapter.items?.add(TransactionHistoryData("David****@paytm","8000","20 min ago"))
        transactionHistoryAdapter.items?.add(TransactionHistoryData("William****@paytm","9000","2 hr ago"))
        transactionHistoryAdapter.items?.add(TransactionHistoryData("Richard****@paytm","10000","19 min ago"))
        transactionHistoryAdapter.items?.add(TransactionHistoryData("Joseph****@paytm","12000","25 min ago"))
        transactionHistoryAdapter.items?.add(TransactionHistoryData("Thomas****@paytm","2000","10 hr ago"))

        transactionHistoryAdapter.items?.add(TransactionHistoryData("Charles****@paytm","5000","3 day ago"))
        transactionHistoryAdapter.items?.add(TransactionHistoryData("Christopher****@paytm","4000","19 min ago"))
        transactionHistoryAdapter.items?.add(TransactionHistoryData("Daniel****@paytm","9000","5 hr ago"))
        transactionHistoryAdapter.items?.add(TransactionHistoryData("Matthew****@paytm","3000","4 hr ago"))
        transactionHistoryAdapter.items?.add(TransactionHistoryData("Anthony****@paytm","7000","19 min ago"))

        transactionHistoryAdapter.items?.add(TransactionHistoryData("Mark****@paytm","6000","68 day ago"))
        transactionHistoryAdapter.items?.add(TransactionHistoryData("Donald****@paytm","12000","60 min ago"))
        transactionHistoryAdapter.items?.add(TransactionHistoryData("Steven****@paytm","22000","5 day ago"))
        transactionHistoryAdapter.items?.add(TransactionHistoryData("Paul****@paytm","23000","19 min ago"))
        transactionHistoryAdapter.items?.add(TransactionHistoryData("Andrew****@paytm","28000","2 day ago"))

        transactionHistoryAdapter.items?.add(TransactionHistoryData("Joshua****@paytm","20000","3 min ago"))
        transactionHistoryAdapter.items?.add(TransactionHistoryData("Kenneth****@paytm","5000","2 hr ago"))
        transactionHistoryAdapter.items?.add(TransactionHistoryData("Russell****@paytm","6000","4 min ago"))
        transactionHistoryAdapter.items?.add(TransactionHistoryData("Bobby****@paytm","2000","5 min ago"))
        transactionHistoryAdapter.items?.add(TransactionHistoryData("Mason****@paytm","1000","10 min ago"))
        transactionHistoryAdapter.notifyDataSetChanged()
    }

    private fun setOnClickListener() = with(binding) {
        imageViewWithdraw.setOnClickListener(this@SecondFragment)
    }


    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.imageViewWithdraw -> {
                navigator.load(FourthFragment::class.java).replace(true)
            }
        }
    }

}
