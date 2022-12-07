package com.ludosupreme.ui.home.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.ludosupreme.R
import com.ludosupreme.databinding.SecondFragmentBinding
import com.ludosupreme.di.component.FragmentComponent
import com.ludosupreme.ui.authentication.viewmodel.AuthenticationViewModel
import com.ludosupreme.ui.base.BaseFragment
import com.ludosupreme.ui.home.viewmodel.HomeViewModel


class SecondFragment : BaseFragment(), View.OnClickListener {


    private var _binding: SecondFragmentBinding? = null

    private val binding: SecondFragmentBinding
        get() = _binding!!

    private val authenticationViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[AuthenticationViewModel::class.java]
    }


    private val homeViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]
    }

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


    private fun initView() {

    }


    private fun setOnClickListener() {
        binding.apply {
        }
    }


    override fun onClick(p0: View?) {
        when (p0?.id) {

        }
    }

}
