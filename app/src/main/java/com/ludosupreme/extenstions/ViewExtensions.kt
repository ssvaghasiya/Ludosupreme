package com.ludosupreme.extenstions

import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.text.InputType
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.Fragment
import com.ludosupreme.utils.MainExecutor
import java.util.concurrent.Executor


/** Visibility Extension **/


fun showHide(view: View) {
    view.visibility = if (view.visibility == View.VISIBLE) {
        View.GONE
    } else {
        View.VISIBLE
    }
}


fun Context.mainExecutor(): Executor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
    mainExecutor
} else {
    MainExecutor()
}

fun View.viewGone() {
    visibility = View.GONE
}

fun View.viewShow() {
    visibility = View.VISIBLE
}

fun View.viewInvisible() {
    visibility = View.INVISIBLE
}

fun View.isViewVisible(): Boolean {
    return visibility == View.VISIBLE
}


fun twoDigit(setValue: Float): String {
    return try {
        String.format("%.2f", setValue)
    } catch (e: NumberFormatException) {
        "0.00"
    }
}


/** Get Text**/

fun TextView.getTxt(): String? {
    return text.toString().trim { it <= ' ' }
}

fun getLocal(context: Context): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        context.resources?.configuration?.locales?.get(0)?.country!!
    } else {
        context.resources?.configuration?.locale?.country!!
    }
}

fun getImage(imageName: String, context: Context): Int {
    return context.resources.getIdentifier(imageName, "drawable", context.packageName)
}


fun Fragment.clearNotification() {
    val notifyManager =
        requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notifyManager.cancelAll()
}

fun AppCompatEditText.disable() {
    isFocusable = false
    isFocusableInTouchMode = false
    isClickable = false
    isCursorVisible = false
    isEnabled = false
    inputType = InputType.TYPE_NULL
}

fun AppCompatEditText.enable() {
    isFocusable = true
    isFocusableInTouchMode = true
    isClickable = true
    isCursorVisible = true
    isEnabled = true
}