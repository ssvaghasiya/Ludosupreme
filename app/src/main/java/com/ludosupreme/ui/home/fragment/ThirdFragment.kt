package com.ludosupreme.ui.home.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.SimpleItemAnimator
import com.ludosupreme.R
import com.ludosupreme.databinding.ThirdFragmentBinding
import com.ludosupreme.di.component.FragmentComponent
import com.ludosupreme.ui.base.BaseFragment
import com.ludosupreme.ui.base.adapters.OnRecycleItemClickWithPosition
import com.ludosupreme.ui.home.adapter.HomeTournamentsAdapter
import com.ludosupreme.ui.home.model.TournamentsData


class ThirdFragment : BaseFragment(), View.OnClickListener {

    private var _binding: ThirdFragmentBinding? = null
    private val binding: ThirdFragmentBinding
        get() = _binding!!

    lateinit var homeTournamentsAdapter: HomeTournamentsAdapter

    override fun createLayout(): Int = R.layout.third_fragment

    override fun bindViewWithViewBinding(view: View) {
        _binding = ThirdFragmentBinding.bind(view)
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
        homeTournamentsAdapter =
            HomeTournamentsAdapter(
                object : OnRecycleItemClickWithPosition<TournamentsData> {
                    override fun onClick(t: TournamentsData?, view: View, position: Int) {

                    }
                })
        recyclerViewTournaments.adapter = homeTournamentsAdapter
        homeTournamentsAdapter.items = ArrayList()
//        (recyclerViewTournaments?.itemAnimator as SimpleItemAnimator).supportsChangeAnimations =
//            false

        homeTournamentsAdapter.items?.add(TournamentsData("4 player-3 winner","5000","18000","8000","6000","4000","0",120000))
        homeTournamentsAdapter.items?.add(TournamentsData("2 player- 1 winner","2000","3500","3500","0",null,null,120000))
        homeTournamentsAdapter.items?.add(TournamentsData("4 player-3 winner","1500","5500","2300","1800","1400","0",120000))
        homeTournamentsAdapter.items?.add(TournamentsData("4 player-3 winner","1000","3700","1500","1300","900","0",120000))

        homeTournamentsAdapter.items?.add(TournamentsData("4 player-3 winner","200","750","350","250","150","0",120000))
        homeTournamentsAdapter.items?.add(TournamentsData("4 player-3 winner","400","1500","650","500","350","0",120000))
        homeTournamentsAdapter.items?.add(TournamentsData("2 player- 1 winner","500","900","900","0",null,null,120000))
        homeTournamentsAdapter.items?.add(TournamentsData("2 player- 1 winner","800","1400","1400","0",null,null,120000))
        homeTournamentsAdapter.items?.add(TournamentsData("2 player- 1 winner","900","1700","1700","0",null,null,120000))
        homeTournamentsAdapter.items?.add(TournamentsData("2 player- 1 winner","1100","2090","2090","0",null,null,120000))
        homeTournamentsAdapter.items?.add(TournamentsData("2 player- 1 winner","2500","4800","4800","0",null,null,120000))
        homeTournamentsAdapter.items?.add(TournamentsData("2 player- 1 winner","4000","7750","7750","0",null,null,120000))
        homeTournamentsAdapter.items?.add(TournamentsData("2 player- 1 winner","8000","15600","15600","0",null,null,120000))

        homeTournamentsAdapter.notifyDataSetChanged()
    }


    private fun setOnClickListener() = with(binding) {
        imageViewBack.setOnClickListener(this@ThirdFragment)
    }


    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.imageViewBack -> {
                navigator.goBack()
            }
        }
    }

}
