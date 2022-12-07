package com.ludosupreme.utils.customLoader

import android.os.Bundle
import android.view.View
import com.ludosupreme.R
import com.ludosupreme.databinding.DialogCustomLoaderBinding
import com.ludosupreme.di.component.DialogComponent
import com.ludosupreme.ui.base.BaseDialog


class CustomLoaderDialog(val message: String) : BaseDialog() {

    private var _binding: DialogCustomLoaderBinding? = null


    override fun createLayout() = R.layout.dialog_custom_loader


    fun showLoader() {
        _binding?.aviLoader?.smoothToShow()
        dialog?.show()

    }

    fun hideLoader() {
        if (dialog!!.isShowing) {
            dialog?.dismiss()
            _binding?.aviLoader?.smoothToHide()
        }
    }

    override fun onStart() {
        isCancelable = false
        super.onStart()
    }


    fun isShowing(): Boolean {
        return dialog!!.isShowing
    }

    override fun bindData() {
        isCancelable = false
        _binding?.apply {
            tvLoaderMsg.text = message
            aviLoader.smoothToShow()
        }
    }

    override fun bindViewWithViewBinding(view: View) {
        _binding = DialogCustomLoaderBinding.bind(view)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding?.apply {
            tvLoaderMsg.text = message
        }
    }

    override fun inject(dialogComponent: DialogComponent) {
        dialogComponent.inject(this)
    }


}