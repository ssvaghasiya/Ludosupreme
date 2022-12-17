package com.ludosupreme.ui.home.fragment

import android.os.Bundle
import android.view.View
import com.ludosupreme.R
import com.ludosupreme.databinding.FourthFragmentBinding
import com.ludosupreme.databinding.ThirdFragmentBinding
import com.ludosupreme.di.component.FragmentComponent
import com.ludosupreme.exception.ApplicationException
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
        buttonSubmit.setOnClickListener(this@FourthFragment)
    }


    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.imageViewBack -> {
                navigator.goBack()
            }
            R.id.buttonSubmit -> {
                if (isValidate()) {
                    showToast("Withdraw successfully")
                    navigator.goBack()
                }
            }
        }
    }

    private fun isValidate(): Boolean {
        try {
            validator.submit(binding.editTextRealName).checkEmpty()
                .errorMessage(getString(R.string.validation_real_name))
                .check()

            validator.submit(binding.editTextAccountNumber).checkEmpty()
                .errorMessage(getString(R.string.validation_account_number)).checkMinDigits(15)
                .errorMessage(getString(R.string.validation_account_number_contains_minimum_15_characters))
                .check()

            validator.submit(binding.editTextConfirmAccountNumber).checkEmpty()
                .errorMessage(getString(R.string.validation_confirm_account_number))
                .check()


            validator.submit(binding.editTextAccountNumber)
                .matchString(binding.editTextConfirmAccountNumber.text?.trim().toString())
                .errorMessage(getString(R.string.validation_account_number_match)).check()

            validator.submit(binding.editTextIFSCCode).checkEmpty()
                .errorMessage(getString(R.string.validation_sort_code))
                .check()

            validator.submit(binding.editTextEmail).checkEmpty()
                .errorMessage(getString(R.string.validation_enter_email))
                .checkValidEmail()
                .errorMessage(getString(R.string.validation_invalid_email))
                .check()

            validator.submit(binding.editTextBank).checkEmpty()
                .errorMessage(getString(R.string.validation_bank_name))
                .check()

            validator.submit(binding.editTextMobile).checkEmpty()
                .errorMessage(getString(R.string.validation_enter_phone)).checkMinDigits(8)
                .errorMessage(getString(R.string.validation_enter_valid_number)).check()

        } catch (e: ApplicationException) {
            showToast(e.message)
            return false
        }
        return true
    }

}
