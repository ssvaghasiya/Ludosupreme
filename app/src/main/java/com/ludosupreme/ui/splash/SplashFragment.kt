package com.ludosupreme.ui.splash

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.lifecycle.ViewModelProvider
import com.ludosupreme.R
import com.ludosupreme.databinding.AuthFragmentSplashBinding
import com.ludosupreme.di.component.FragmentComponent
import com.ludosupreme.ui.authentication.viewmodel.AuthenticationViewModel
import com.ludosupreme.ui.base.BaseFragment
import com.ludosupreme.ui.home.activity.HomeActivity
import com.ludosupreme.ui.home.fragment.SecondFragment
import com.ludosupreme.utils.Constant.SignUpType.IS_INTRO
import com.ludosupreme.utils.Debug
import com.ludosupreme.utils.Utils.getHashKey


class SplashFragment : BaseFragment() {

    private val authenticationViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[AuthenticationViewModel::class.java]
    }

    private var _binding: AuthFragmentSplashBinding? = null

    private val binding: AuthFragmentSplashBinding
        get() = _binding!!

    private var countDownTimer: CountDownTimer? = null

    override fun createLayout(): Int = R.layout.auth_fragment_splash

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun bindViewWithViewBinding(view: View) {
        _binding = AuthFragmentSplashBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun inject(fragmentComponent: FragmentComponent) = fragmentComponent.inject(this)

    override fun bindData() {
        toolbar.showToolbar(false)
        getHashKey(requireContext())
        countDownTimer = object : CountDownTimer(3000, 1000) {
            override fun onFinish() {
                if (session.user == null) {
                    navigator.loadActivity(HomeActivity::class.java).byFinishingCurrent().start()
                } else {
                    navigator.loadActivity(HomeActivity::class.java).byFinishingCurrent().start()
                }
            }

            override fun onTick(millisUntilFinished: Long) {
            }
        }

    }

    override fun destroyViewBinding() {
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        countDownTimer?.start()
    }

    override fun onPause() {
        super.onPause()
        countDownTimer?.cancel()
    }
}