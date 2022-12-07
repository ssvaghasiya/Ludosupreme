package com.ludosupreme.ui.home.activity

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.ludosupreme.R
import com.ludosupreme.databinding.HomeActivityBinding
import com.ludosupreme.di.component.ActivityComponent
import com.ludosupreme.extenstions.updateStatusBarColor
import com.ludosupreme.ui.base.BaseActivity
import com.ludosupreme.utils.Constant
import com.ludosupreme.utils.Constant.PassValue.NOTIFICATION_CLICK


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
        updateStatusBarColor(R.color.backgroundColor)
        load(HomeFragment::class.java).setBundle(
            bundleOf(NOTIFICATION_CLICK to intent.getStringExtra(Constant.PassValue.NOTIFICATION_CLICK))
        ).add(false)

        initView()
        setOnClickListener()
    }

    private fun initView() {

    }

    private fun setOnClickListener() {
        binding.apply {
            linearSettings.setOnClickListener(this@HomeActivity)
            linearRecipient.setOnClickListener(this@HomeActivity)
            imageViewAudio.setOnClickListener(this@HomeActivity)
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.linearSettings -> {
            }
            R.id.linearRecipient -> {

            }
            R.id.imageViewAudio -> {
            }

        }
    }

    override fun onBackPressed() {
        finishAffinity()
        finish()
    }


}
