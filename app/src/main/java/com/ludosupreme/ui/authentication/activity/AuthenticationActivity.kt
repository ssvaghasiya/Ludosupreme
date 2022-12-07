package com.ludosupreme.ui.authentication.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import com.ludosupreme.R
import com.ludosupreme.databinding.AuthenticationActivityBinding
import com.ludosupreme.di.component.ActivityComponent
import com.ludosupreme.extenstions.makeStatusBarTransparent
import com.ludosupreme.extenstions.setMarginTop
import com.ludosupreme.ui.base.BaseActivity
import com.ludosupreme.ui.base.BaseFragment
import com.ludosupreme.ui.manager.ActivityStarter
import com.ludosupreme.ui.splash.SplashFragment

class AuthenticationActivity : BaseActivity() {
    lateinit var binding: AuthenticationActivityBinding

    override fun findFragmentPlaceHolder(): Int = R.id.placeHolder
    override fun findContentView(): Int = R.layout.authentication_activity
    override fun inject(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun bindViewWithViewBinding(view: View) {
        binding = AuthenticationActivityBinding.bind(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        makeStatusBarTransparent()
        setMarginZero()
        if (savedInstanceState == null) {
            val page =
                intent.getSerializableExtra(ActivityStarter.ACTIVITY_FIRST_PAGE) as? Class<BaseFragment>

            if (page != null) {
                load(page)
                    .setBundle(intent.extras!!)
                    .add(false)
            } else {
                load(SplashFragment::class.java).replace(false)
            }
        } else {
            load(SplashFragment::class.java).replace(false)
        }
    }

    fun setMarginZero() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.mainContent.coordinatorLayout) { _, insets ->
            binding.mainContent.coordinatorLayout.setMarginTop(0)
            insets.consumeSystemWindowInsets()
        }
    }

    fun setMargin() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.mainContent.coordinatorLayout) { _, insets ->
            binding.mainContent.coordinatorLayout.setMarginTop(insets.systemWindowInsetTop)
            insets.consumeSystemWindowInsets()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        for (fragment in supportFragmentManager.fragments) {
            fragment.onActivityResult(requestCode, resultCode, data)
        }
    }
}
