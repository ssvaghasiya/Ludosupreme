package com.ludosupreme.ui.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.ludosupreme.utils.Validator
import com.ludosupreme.BuildConfig
import com.ludosupreme.R
import com.ludosupreme.core.AppPreferences
import com.ludosupreme.core.Session
import com.ludosupreme.di.HasComponent
import com.ludosupreme.di.component.ActivityComponent
import com.ludosupreme.di.component.FragmentComponent
import com.ludosupreme.di.module.FragmentModule
import com.ludosupreme.ui.authentication.activity.AuthenticationActivity
import com.ludosupreme.ui.home.activity.HomeActivity
import com.ludosupreme.ui.manager.Navigator
import javax.inject.Inject


abstract class BaseFragment : Fragment(), HasComponent<FragmentComponent> {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var validator: Validator

    @Inject
    lateinit var session: Session

    @Inject
    lateinit var appPreferences: AppPreferences


    protected lateinit var toolbar: HasToolbar


    override val component: FragmentComponent
        get() {
            return getComponent(ActivityComponent::class.java).plus(FragmentModule(this))
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bindData()
    }

    protected fun <C> getComponent(componentType: Class<C>): C {
        return componentType.cast((activity as HasComponent<C>).component)
    }

    override fun onAttach(context: Context) {
        inject(component)
        super.onAttach(context)

        if (activity is HasToolbar)
            toolbar = activity as HasToolbar


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


    fun <T : BaseFragment> getParentFragment(targetFragment: Class<T>): T? {
        if (parentFragment == null) return null
        try {
            return targetFragment.cast(parentFragment)
        } catch (e: ClassCastException) {
            e.printStackTrace()
        }

        return null
    }

    open fun onShow() {

    }

    open fun onBackActionPerform(): Boolean {
        return true
    }

    open fun onViewClick(view: View) {

    }

    override fun onDestroyView() {
        destroyViewBinding()
        super.onDestroyView()
    }


    public fun onError(throwable: Throwable) {
        (activity as BaseActivity).onError(throwable)
    }

    fun showLoader(isShow: Boolean) {
        if (activity is BaseActivity) {
            (activity as BaseActivity).toggleLoader(isShow)
        }
    }

    private fun onLogoutOpenAuthentication() {
        navigator.loadActivity(AuthenticationActivity::class.java).byFinishingAll().start()
    }

    protected abstract fun createLayout(): Int

    /**
     * This method is used for binding view with your binding
     */
    protected abstract fun bindViewWithViewBinding(view: View)

    protected abstract fun inject(fragmentComponent: FragmentComponent)
    protected abstract fun bindData()
    protected abstract fun destroyViewBinding()

    protected fun showToast(msg: String) {
        (activity as BaseActivity).showToast(msg)
    }

    open fun showMessage(message: String) {
        val snackBar = Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT)
        val snackView = snackBar.view

        snackView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.black))
        //val textView = snackView.findViewById<TextView>(android.support.design.R.id.snackbar_text)
        val textView =
            snackView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        textView.maxLines = 4
        textView.setTextColor(this.resources.getColor(R.color.white))
        snackBar.show()

    }

    fun openHomeScreen() {
        navigator.loadActivity(HomeActivity::class.java).byFinishingAll().start()
    }

    fun getStatusBarHeight(): Int {
        return (activity as BaseActivity).getStatusBarHeight()
    }

    /*fun FragmentManager.replaceTab(
        fragmentList: List<BaseFragment>,
        fragment: BaseFragment
    ) {

        val fragmentTransaction = beginTransaction()

        if (fragment.isAdded) {
            fragmentTransaction.show(fragment)
        } else {
            childFragmentManager.executePendingTransactions()
            fragmentTransaction.add(R.id.frameLayout, fragment)
        }
        fragmentList.forEach {
            if (fragment != it) {
                if (it.isAdded) {
                    fragmentTransaction.hide(it)
                }
            }
        }

        fragmentTransaction.commit()
    }*/


    fun shareApp() {
        try {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))
            val msg = ""
            val shareMessage =
                """
                ${msg}https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}
                """.trim()
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
            startActivity(Intent.createChooser(shareIntent, getString(R.string.app_name)))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}
