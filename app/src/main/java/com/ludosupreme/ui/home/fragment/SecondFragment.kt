package com.ludosupreme.ui.home.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.ludosupreme.R
import com.ludosupreme.databinding.SecondFragmentBinding
import com.ludosupreme.di.component.FragmentComponent
import com.ludosupreme.ui.base.BaseFragment
import com.ludosupreme.ui.base.adapters.OnRecycleItemClickWithPosition
import com.ludosupreme.ui.home.adapter.HomeAdsAdapter


class SecondFragment : BaseFragment(), View.OnClickListener {

    private var _binding: SecondFragmentBinding? = null
    private val binding: SecondFragmentBinding
        get() = _binding!!

    lateinit var adsAdapter: HomeAdsAdapter

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

//        dotsIndicator.attachToRecyclerView(recyclerViewLudo)

        adsAdapter.items?.add("")
        adsAdapter.items?.add("")
        adsAdapter.items?.add("")
        adsAdapter.notifyDataSetChanged()
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
