package com.ludosupreme.ui.home.activity

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import com.ludosupreme.R
import com.ludosupreme.databinding.HomeActivityBinding
import com.ludosupreme.di.component.ActivityComponent
import com.ludosupreme.extenstions.setMarginTop
import com.ludosupreme.extenstions.updateStatusBarColor
import com.ludosupreme.ui.base.BaseActivity
import com.ludosupreme.ui.home.fragment.SecondFragment
import com.ludosupreme.utils.Constant
import com.ludosupreme.utils.Constant.PassValue.NOTIFICATION_CLICK
import com.ludosupreme.utils.Debug


class HomeActivity : BaseActivity(), View.OnClickListener {

    lateinit var binding: HomeActivityBinding

    override fun findFragmentPlaceHolder(): Int = R.id.placeHolder
    override fun findContentView(): Int = R.layout.home_activity
    override fun inject(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun bindViewWithViewBinding(view: View) {
        binding = HomeActivityBinding.bind(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Debug.e("Home==")
        initView()
        setOnClickListener()
        load(SecondFragment::class.java).add(false)
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

    override fun onBackPressed() {
        finishAffinity()
        finish()
    }

    fun setMarginZero() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.coordinatorLayout) { _, insets ->
            binding.coordinatorLayout.setMarginTop(0)
            insets.consumeSystemWindowInsets()
        }
    }

    fun setMargin() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.coordinatorLayout) { _, insets ->
            binding.coordinatorLayout.setMarginTop(insets.systemWindowInsetTop)
            insets.consumeSystemWindowInsets()
        }
    }

}
