package com.ludosupreme.ui.home.fragment

import android.os.Bundle
import android.view.View
import com.ludosupreme.R
import com.ludosupreme.databinding.FourthFragmentBinding
import com.ludosupreme.databinding.ThirdFragmentBinding
import com.ludosupreme.di.component.FragmentComponent
import com.ludosupreme.ui.base.BaseFragment
import com.ludosupreme.ui.base.adapters.OnRecycleItemClickWithPosition
import com.ludosupreme.ui.home.adapter.HomeTournamentsAdapter


class FourthFragment : BaseFragment(), View.OnClickListener {

    private var _binding: FourthFragmentBinding? = null
    private val binding: FourthFragmentBinding
        get() = _binding!!

    lateinit var homeTournamentsAdapter: HomeTournamentsAdapter

    override fun createLayout(): Int = R.layout.fourth_fragment

    override fun bindViewWithViewBinding(view: View) {
        _binding = FourthFragmentBinding.bind(view)
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


    override fun bindData() {
        initView()
        setOnClickListener()
    }


    private fun initView() = with(binding) {

    }


    private fun setOnClickListener() = with(binding) {
        imageViewBack.setOnClickListener(this@FourthFragment)
    }


    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.imageViewBack -> {
                navigator.goBack()
            }
        }
    }

}
