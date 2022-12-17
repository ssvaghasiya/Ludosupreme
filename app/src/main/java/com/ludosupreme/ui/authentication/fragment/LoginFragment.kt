package com.ludosupreme.ui.authentication.fragment

import android.content.Intent
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ludosupreme.exception.ApplicationException
import com.ludosupreme.R
import com.ludosupreme.data.URLFactory
import com.ludosupreme.data.pojo.ApiRequestData
import com.ludosupreme.data.pojo.FbRes
import com.ludosupreme.databinding.AuthFragmentLoginBinding
import com.ludosupreme.di.component.FragmentComponent
import com.ludosupreme.extenstions.getTxt
import com.ludosupreme.extenstions.hideKeyboard
import com.ludosupreme.ui.authentication.activity.AuthenticationActivity
import com.ludosupreme.ui.authentication.viewmodel.AuthenticationViewModel
import com.ludosupreme.ui.base.BaseFragment
import com.ludosupreme.utils.*
import com.ludosupreme.utils.Utils.getDeviceInfo
import org.json.JSONObject


class LoginFragment : BaseFragment(), View.OnClickListener {

    private var _binding: AuthFragmentLoginBinding? = null

    private val binding: AuthFragmentLoginBinding
        get() = _binding!!

    private val authenticationViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[AuthenticationViewModel::class.java]
    }

    override fun createLayout(): Int = R.layout.auth_fragment_login

    override fun bindViewWithViewBinding(view: View) {
        _binding = AuthFragmentLoginBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun bindData() {
        toolbar.apply {
            showToolbar(true)
            setToolbarTitle("")
            showImageViewGroupCircle(true)
        }
        initView()
        setOnClickListener()
    }

    private fun initView() {

    }


    private fun setOnClickListener() {

    }


    override fun destroyViewBinding() {
        _binding = null
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {

        }
    }

    private fun isValidate(): Boolean {
        try {

        } catch (e: ApplicationException) {
            showMessage(e.message)
            return false
        }
        return true
    }

}
