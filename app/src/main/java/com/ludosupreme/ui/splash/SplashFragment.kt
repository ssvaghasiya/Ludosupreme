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
        getCredentialsDataObserver()
        callGetCredentials()
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


    private fun callGetCredentials() {
        authenticationViewModel.getCredentials()
    }


    private fun getCredentialsDataObserver() {
        authenticationViewModel.getCredentialsLiveData.observe(this, { responseBody ->
            Debug.e("Success...  " + responseBody.responseCode)
            session.credential = responseBody.data
        }
        )
        { throwable, response ->
            Debug.e(throwable.message)
            false
        }
    }

}