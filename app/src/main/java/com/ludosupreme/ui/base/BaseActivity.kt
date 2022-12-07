package com.ludosupreme.ui.base

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.appbar.AppBarLayout
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.ludosupreme.exception.ApplicationException
import com.theartofdev.edmodo.cropper.CropImage
import com.ludosupreme.exception.AuthenticationException
import com.ludosupreme.R
import com.ludosupreme.core.AppPreferences
import com.ludosupreme.core.Session
import com.ludosupreme.di.App
import com.ludosupreme.di.HasComponent
import com.ludosupreme.di.Injector
import com.ludosupreme.di.component.ActivityComponent
import com.ludosupreme.di.component.DaggerActivityComponent
import com.ludosupreme.exception.ServerException
import com.ludosupreme.extenstions.*
import com.ludosupreme.ui.authentication.activity.AuthenticationActivity
import com.ludosupreme.ui.manager.*
import com.ludosupreme.utils.Debug
import com.ludosupreme.utils.customLoader.CustomLoaderDialog
import kotlinx.android.synthetic.main.main_content.*
import java.net.ConnectException
import java.net.SocketTimeoutException
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity(), HasComponent<ActivityComponent>, HasToolbar,
    Navigator {
    override val component: ActivityComponent
        get() = activityComponent

    @Inject
    lateinit var navigationFactory: FragmentNavigationFactory

    @Inject
    lateinit var activityStarter: ActivityStarter

    @Inject
    lateinit var appPreferences: AppPreferences

    @Inject
    lateinit var session: Session

    private var isBack = false

    private var customLoaderDialog: CustomLoaderDialog? = null

    private var isCustomLoaderShowing: Boolean = false

    //protected var toolbar: Toolbar? = null
    //protected var toolbarTitle: AppCompatTextView? = null
    internal var progressDialog: ProgressDialog? = null
    internal var alertDialog: AlertDialog? = null

    private lateinit var activityComponent: ActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        customLoaderDialog =
            CustomLoaderDialog(resources.getString(R.string.label_loading_please_wait))

        activityComponent = DaggerActivityComponent.builder()
            .bindApplicationComponent(Injector.INSTANCE.applicationComponent)
            .bindActivity(this)
            .build()

        inject(activityComponent)



        setContentView(findContentView())
        bindViewWithViewBinding((findViewById<ViewGroup>(android.R.id.content)).getChildAt(0))

        //findViewById<>()
        /*if (toolbar != null)
            setSupportActionBar(toolbar)*/

        if (appToolbar != null)
            setSupportActionBar(appToolbar)

        val actionbar = supportActionBar
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true)
            actionbar.setDisplayShowTitleEnabled(false)
            actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white)
        }

        setUpAlertDialog()

        progressDialog = ProgressDialog(this)
        progressDialog!!.setMessage("Please wait...")
        progressDialog!!.setCancelable(false)
        progressDialog!!.setCanceledOnTouchOutside(false)

        App.session = session
        FirebaseApp.initializeApp(this)
        createFirebaseToken()
        super.onCreate(savedInstanceState)

    }


    private fun setUpAlertDialog() {
        alertDialog = AlertDialog.Builder(this)
            .setPositiveButton("ok", null)
            .setTitle(R.string.app_name)
            .create()
    }

    fun <F : BaseFragment> getCurrentFragment(): F? {
        return if (findFragmentPlaceHolder() == 0) null else supportFragmentManager.findFragmentById(
            findFragmentPlaceHolder()
        ) as F
    }

    abstract fun findFragmentPlaceHolder(): Int

    @LayoutRes
    abstract fun findContentView(): Int


    abstract fun inject(activityComponent: ActivityComponent)

    abstract fun bindViewWithViewBinding(view: View)

    fun showErrorMessage(message: String?) {
        /*val f = getCurrentFragment<BaseFragment<*, *>>()
        if (f != null)
            Snackbar.make(f.getView()!!, message!!, BaseTransientBottomBar.LENGTH_SHORT).show()*/

    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    /*fun toggleLoader(show: Boolean) {

        if (show) {
            if (!progressDialog!!.isShowing)
                progressDialog!!.show()
        } else {
            if (progressDialog!!.isShowing)
                progressDialog!!.dismiss()
        }
    }*/

    fun toggleLoader(
        show: Boolean,
        message: String = resources.getString(R.string.label_loading_please_wait)
    ) {

        if (show) {
            if (!isCustomLoaderShowing) {
                isCustomLoaderShowing = true

                customLoaderDialog = CustomLoaderDialog(message)
                customLoaderDialog?.show(supportFragmentManager, "")

            }
        } else {
            if (isCustomLoaderShowing) {
                isCustomLoaderShowing = false
                customLoaderDialog?.dismiss()
            }
        }
    }

    protected fun shouldGoBack(): Boolean {
        return true
    }

    override fun onBackPressed() {
        hideKeyboard()


        val currentFragment = getCurrentFragment<BaseFragment>()
        if (currentFragment == null)
            super.onBackPressed()
        else if (currentFragment.onBackActionPerform() && shouldGoBack())
            super.onBackPressed()

        // pending animation
        // overridePendingTransition(R.anim.pop_enter, R.anim.pop_exit);

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun setToolbar(toolbar: Toolbar) {
        if (appToolbar != null) {
            if (this.appToolbar != null) {
                toolbar.visibility = View.GONE
            }
            setSupportActionBar(appToolbar)
        }
    }

    private fun setToolbarMargin(): Int {
        val rectangle = Rect()
        val window = window
        window.decorView.getWindowVisibleDisplayFrame(rectangle)
        val statusBarHeight: Int = rectangle.top
        val contentViewTop = window.findViewById<View>(Window.ID_ANDROID_CONTENT).top
        val titleBarHeight = contentViewTop - statusBarHeight

        Debug.e(
            "*** Elenasys :: ",
            "StatusBar Height= $statusBarHeight , TitleBar Height = $titleBarHeight"
        )
        return statusBarHeight
    }

    override fun showToolbar(b: Boolean) {
        if (appToolbar != null) {
            if (b) {
                appBarLayout.viewShow()
                val params = placeHolder.layoutParams as CoordinatorLayout.LayoutParams
                params.behavior = AppBarLayout.ScrollingViewBehavior()
                appBarLayout.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.backgroundColor
                    )
                )
                appBarLayout.setMarginTop(getStatusBarHeight())
                imageViewGroupCircle.alpha = 0.7F
                placeHolder.setPadding(0, 0, 0, getStatusBarHeight())
                placeHolder.requestLayout()
            } else {
                appBarLayout.viewGone()
                val params = placeHolder.layoutParams as CoordinatorLayout.LayoutParams
                params.behavior = null
                appBarLayout.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        android.R.color.transparent
                    )
                )
                appBarLayout.setMarginTop(0)
                placeHolder.setPadding(0, 0, 0, 0)
                placeHolder.requestLayout()
            }
        }
    }

    override fun showToolbarIcon(b: Boolean) {
        if (appToolbar != null) {

        }
    }

    override fun showImageViewGroupCircle(b: Boolean) {
        if (appToolbar != null) {
            if (b) {
                imageViewGroupCircle.viewShow()
            } else {
                imageViewGroupCircle.viewGone()
            }
        }
    }

    override fun setToolbarColor(color: Int) {
        if (appToolbar != null) {
            appBarLayout.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    color
                )
            )
        }
    }

    override fun setCoordinatorLayoutColor(color: Int) {
        if (appToolbar != null) {
            coordinatorLayout.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    color
                )
            )
        }
    }

    fun getStatusBarHeight(): Int {
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (resourceId > 0) resources.getDimensionPixelSize(resourceId)
        else Rect().apply { window.decorView.getWindowVisibleDisplayFrame(this) }.top
    }


    override fun toolbarTransparent(b: Boolean) {
        if (appToolbar != null) {
            if (b) {
                appToolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent))
                val params = placeHolder.layoutParams as CoordinatorLayout.LayoutParams
                params.behavior = null
                appBarLayout.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        android.R.color.transparent
                    )
                )
                placeHolder.requestLayout()
            } else {
                appToolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
                val params = placeHolder.layoutParams as CoordinatorLayout.LayoutParams
                params.behavior = AppBarLayout.ScrollingViewBehavior()
                appBarLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
                placeHolder.requestLayout()

            }
        }
    }


    override fun setToolbarTitle(title: String) {
        if (appToolbar != null) {
            textViewMainTitle.text = title
            textViewMainTitle.setTextColor(ContextCompat.getColor(this, R.color.white))
        }
    }

    override fun showBackButton(b: Boolean) {
        this.isBack = b
        val supportActionBar = supportActionBar
        if (b) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white)
        } else {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }
    }


    fun showKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val inputManager =
                this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    fun hideKeyboard() {
        // Check if no view has focus:

        val view = this.currentFocus
        if (view != null) {
            val inputManager =
                this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(
                view.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }

    }

    /*   private fun createFirebaseToken() {
           FirebaseMessaging.getInstance().token.addOnSuccessListener {
               Debug.e("Token", it)
               session.deviceId = it
           }
       }*/

    private fun createFirebaseToken() {
        try {
            FirebaseMessaging.getInstance().token
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        return@OnCompleteListener
                    }
                    //                          Get new Instance ID token
                    val token = task.result
                    try {
                        session.deviceId = token
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    Debug.e("fcm_token", token)
                })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun <T : BaseFragment> load(tClass: Class<T>): FragmentActionPerformer<T> {
        return navigationFactory.make(tClass)
    }

    override fun loadActivity(aClass: Class<out BaseActivity>): ActivityBuilder {
        return activityStarter.make(aClass)
    }

    override fun <T : BaseFragment> loadActivity(
        aClass: Class<out BaseActivity>,
        pageTClass: Class<T>
    ): ActivityBuilder {
        return activityStarter.make(aClass).setPage(pageTClass)
    }


    override fun goBack() {
        onBackPressed()
    }

    public fun onError(throwable: Throwable) {
        Log.e(javaClass.simpleName, "Error From Base framework ", throwable)
        try {
            if (throwable is ServerException) {
                showErrorMsg(throwable.message ?: "")
            } else if (throwable is ConnectException) {
                showToast(getString(R.string.connect_to_internet))
            } else if (throwable is ApplicationException) {
                showToast(throwable.message)
            } else if (throwable is AuthenticationException) {
                onLogoutOpenAuthentication()
            } else if (throwable is SocketTimeoutException) {
                showToast(getString(R.string.conn_timeout))
            } else {
                showToast(getString(R.string.something_went_wrong))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun onLogoutOpenAuthentication() {
        session.user = null
        session.userSession = ""
        session.clearSession()
//        loadActivity(AuthenticationActivity::class.java)
//            .setPage(RegisterLoginFragment::class.java).byFinishingAll().start()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            getCurrentFragment<BaseFragment>()?.onActivityResult(requestCode, resultCode, data)
        }

    }

}
