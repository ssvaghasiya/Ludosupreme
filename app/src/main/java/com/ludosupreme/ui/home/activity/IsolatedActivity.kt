package com.ludosupreme.ui.home.activity

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import com.ludosupreme.R
import com.ludosupreme.databinding.IsolatedActivityBinding
import com.ludosupreme.di.component.ActivityComponent
import com.ludosupreme.extenstions.makeStatusBarDarkTransparent
import com.ludosupreme.extenstions.setMarginTop
import com.ludosupreme.ui.base.BaseActivity
import com.ludosupreme.ui.base.BaseFragment
import com.ludosupreme.ui.manager.ActivityStarter
import com.ludosupreme.utils.Constant
import com.ludosupreme.utils.Constant.PushTag.VOICEM_USER_REQUEST

class IsolatedActivity : BaseActivity() {
    lateinit var binding: IsolatedActivityBinding

    override fun findFragmentPlaceHolder(): Int = R.id.placeHolder
    override fun findContentView(): Int = R.layout.isolated_activity
    override fun inject(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun bindViewWithViewBinding(view: View) {
        binding = IsolatedActivityBinding.bind(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        makeStatusBarDarkTransparent()
        setMarginZero()
        if (supportActionBar != null)
            supportActionBar!!.setDisplayShowTitleEnabled(false)
        var requestNotification = intent.getStringExtra(Constant.PushTagKEYS.PUSH_TAG_REQUEST)
        if (savedInstanceState == null) {
            if (requestNotification.isNullOrEmpty()
                    .not() && requestNotification == VOICEM_USER_REQUEST
            ) {

            } else {
                val page =
                    intent.getSerializableExtra(ActivityStarter.ACTIVITY_FIRST_PAGE) as Class<BaseFragment>
                load(page).setBundle(intent.extras!!).replace(false)
            }

        }

    }

    private fun setMarginZero() {
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

    override fun setCoordinatorLayoutColor(color: Int) {
        binding.mainContent.coordinatorLayout.setBackgroundColor(
            ContextCompat.getColor(
                this,
                color
            )
        )
        super.setCoordinatorLayoutColor(color)

    }
}
