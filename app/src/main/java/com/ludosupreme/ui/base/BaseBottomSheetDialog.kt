package com.ludosupreme.ui.base

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import androidx.annotation.NonNull
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ludosupreme.R
import com.ludosupreme.core.Session
import com.ludosupreme.di.HasComponent
import com.ludosupreme.di.component.ActivityComponent
import com.ludosupreme.di.component.BottomSheetDialogComponent
import com.ludosupreme.di.module.BottomSheetDialogModule
import com.ludosupreme.ui.manager.Navigator
import javax.inject.Inject

abstract class BaseBottomSheetDialog : BottomSheetDialogFragment(),
    HasComponent<BottomSheetDialogComponent> {


    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var session: Session

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    override val component: BottomSheetDialogComponent
        get() {
            return getComponent(ActivityComponent::class.java).plus(BottomSheetDialogModule(this))
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.CustomBottomSheetDialogTheme);
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(createLayout(), container, false)
        bindViewWithViewBinding(view)
        return view
    }

    @NonNull
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog: Dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener(DialogInterface.OnShowListener { dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            setupFullHeight(bottomSheetDialog)
        })
        return dialog
    }

    private fun setupFullHeight(bottomSheetDialog: BottomSheetDialog) {
        val bottomSheet =
            bottomSheetDialog.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout?
        val behavior: BottomSheetBehavior<*> = BottomSheetBehavior.from<FrameLayout?>(bottomSheet!!)
        val layoutParams = bottomSheet.layoutParams
        val windowHeight = getWindowHeight()
        if (layoutParams != null) {
//            layoutParams.height = windowHeight - 180
            layoutParams.height = bottomSheet.layoutParams.height
        }
        bottomSheet.layoutParams = layoutParams
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun getWindowHeight(): Int {
        // Calculate window height for fullscreen use
        val displayMetrics = DisplayMetrics()
        (context as Activity?)!!.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    fun toggleLoader(show: Boolean) {
        if (activity is BaseActivity) {
            (activity as BaseActivity).toggleLoader(show)
        }
    }

    protected fun <C> getComponent(componentType: Class<C>): C {
        return componentType.cast((activity as HasComponent<C>).component)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bindData()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject(component)
    }

    fun showLoader(isShow: Boolean) {
        if (activity is BaseActivity) {
            (activity as BaseActivity).toggleLoader(isShow)
        }
    }

    fun hideKeyBoard() {
        if (activity is BaseActivity) {
            (activity as BaseActivity).hideKeyboard()
        }
    }

    fun showKeyBoard() {
        if (activity is BaseActivity) {
            (activity as BaseActivity).showKeyboard()
        }
    }

    fun hideKeyBoard(view: View?) {
        if (view != null) {
            val inputManager =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(
                view.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }


    fun showCustomToast(toastMessage: String) {

        (activity as BaseActivity).showToast(toastMessage)

    }


    protected abstract fun inject(dialogComponent: BottomSheetDialogComponent)
    protected abstract fun createLayout(): Int
    protected abstract fun bindData()
    protected abstract fun bindViewWithViewBinding(view: View)

    fun onError(throwable: Throwable) {
        (activity as BaseActivity).onError(throwable)
    }

}

