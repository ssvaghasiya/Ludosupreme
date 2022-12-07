package com.ludosupreme.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.ludosupreme.R
import com.ludosupreme.core.AppPreferences
import com.ludosupreme.core.Session
import com.ludosupreme.di.HasComponent
import com.ludosupreme.di.component.ActivityComponent
import com.ludosupreme.di.component.DialogComponent
import com.ludosupreme.di.module.DialogModule
import com.ludosupreme.ui.manager.Navigator
import javax.inject.Inject


abstract class BaseDialog : DialogFragment(), HasComponent<DialogComponent> {


    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var session: Session

    @Inject
    lateinit var appPreferences: AppPreferences

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    override val component: DialogComponent
        get() {
            return getComponent(ActivityComponent::class.java).plus(DialogModule(this))
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


//        if (this is RenameDialogLive) {
        isCancelable = true
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog?.window?.attributes!!.windowAnimations = R.style.DialogAnimationwithFade
//        } else {
//            dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
//            dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
//            dialog?.window?.setGravity(Gravity.BOTTOM)
//            dialog?.window?.attributes!!.windowAnimations = R.style.DialogAnimationfrombootom
//        }

        val view = inflater.inflate(createLayout(), container, false)
        bindViewWithViewBinding(view)
        return view
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


    fun hideKeyBoard() {
        if (activity is BaseActivity) {
            (activity as BaseActivity).hideKeyboard()
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

    fun showKeyBoard() {
        if (activity is BaseActivity) {
            (activity as BaseActivity).showKeyboard()
        }
    }

    fun showCustomToast(toastMessage: String) {

        (activity as BaseActivity).showToast(toastMessage)

    }


    protected abstract fun inject(dialogComponent: DialogComponent)
    protected abstract fun createLayout(): Int
    protected abstract fun bindData()
    protected abstract fun bindViewWithViewBinding(view: View)

    fun onError(throwable: Throwable) {
        (activity as BaseActivity).onError(throwable)
    }

}

