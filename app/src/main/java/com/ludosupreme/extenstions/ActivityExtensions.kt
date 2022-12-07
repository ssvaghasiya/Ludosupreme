package com.ludosupreme.extenstions

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.tapadoo.alerter.Alerter
import com.ludosupreme.R
import com.ludosupreme.exception.NoInternetException
import com.ludosupreme.utils.CallbackListener

/**
 * Show Keyboard.
 */
fun AppCompatActivity.showKeyboard() {
    val view = this.currentFocus
    if (view != null) {
        val inputManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }
}

/**
 * Hide Keyboard.
 */
fun AppCompatActivity.hideKeyboard() {
    val view = this.currentFocus
    if (view != null) {
        val inputManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}

fun Activity.makeStatusBarTransparent() {
    window.apply {
        clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        } else {
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        statusBarColor = Color.TRANSPARENT
    }
}

fun Activity.makeStatusBarDarkTransparent() {
    window.apply {
        clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        statusBarColor = Color.TRANSPARENT
    }
}

fun Activity.hideStatusBar() {
    window.apply {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }
}

fun View.setMarginTop(marginTop: Int) {
    val menuLayoutParams = this.layoutParams as ViewGroup.MarginLayoutParams
    menuLayoutParams.setMargins(0, marginTop, 0, 0)
    this.layoutParams = menuLayoutParams
}

fun checkedImage(checkImage: AppCompatImageView, uncheckList: ArrayList<AppCompatImageView>) {
    checkImage.isSelected = true
    uncheckList.forEach {
        it.isSelected = false
    }
}

fun selectedTextView(
    selectedTextView: AppCompatTextView,
    uncheckList: java.util.ArrayList<AppCompatTextView>
) {
    selectedTextView.isSelected = true
    uncheckList.forEach {
        it.isSelected = false
    }
}


@Throws(NoInternetException::class)
fun isInternetConnected(mContext: Context?): Boolean {
    var outcome = false
    try {
        if (mContext != null) {
            val cm = mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = cm.activeNetworkInfo
            if (networkInfo!!.isConnected) {
                outcome = true
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return outcome
}

fun Activity.isInternetAvailable(context: Context, callbackListener: CallbackListener) {
    if (isInternetConnected(context)) {
        callbackListener.onSuccess()
        return
    } else {
        callbackListener.onCancel()
        return
    }
}

fun Activity.showErrorMsg(message: String?) {
    message?.let {
        Alerter.create(this)
            .setTitle(it)
            .setBackgroundColorRes(R.color.green)
            .setTextTypeface(ResourcesCompat.getFont(this, R.font.poppins_bold)!!)
            .setDuration(2000)
            .hideIcon()
            .show()
    }
}