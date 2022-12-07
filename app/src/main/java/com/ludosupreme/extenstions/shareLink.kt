package com.ludosupreme.extenstions

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.view.WindowManager
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

import java.net.Inet4Address
import java.net.NetworkInterface
import java.net.SocketException
import java.util.*
import java.util.concurrent.TimeUnit

fun Context.bitmapToFile(bitmap: Bitmap): File {
    // Get the context wrapper
    val wrapper = ContextWrapper(this)

    // Initialize a new file instance to save bitmap object
    var file = wrapper.getDir("Images", Context.MODE_PRIVATE)
    file = File(file, "${UUID.randomUUID()}.png")

    try {
        // Compress the bitmap and save in jpg format
        val stream: OutputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        stream.flush()
        stream.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return file
    // Return the saved bitmap uri
    //return Uri.parse(file.absolutePath)
}

fun Context.getDayTodayTomorrow(date: String): String? {
    val yesterday = Calendar.getInstance()
    yesterday.add(Calendar.DATE, -1)
    if (date == com.ludosupreme.utils.Formatter.format(
            Date(yesterday.timeInMillis),
            com.ludosupreme.utils.Formatter.YYYY_MM_DD
        )
    )
        return "Yesterday"

    val today = Calendar.getInstance()
    if (date == com.ludosupreme.utils.Formatter.format(
            Date(today.timeInMillis),
            com.ludosupreme.utils.Formatter.YYYY_MM_DD
        )
    )
        return "Today"

    val tomorrow = Calendar.getInstance()
    tomorrow.add(Calendar.DATE, 1)
    if (date == com.ludosupreme.utils.Formatter.format(
            Date(tomorrow.timeInMillis),
            com.ludosupreme.utils.Formatter.YYYY_MM_DD
        )
    )
        return "Tomorrow"

    return com.ludosupreme.utils.Formatter.format(
        date,
        com.ludosupreme.utils.Formatter.YYYY_MM_DD,
        com.ludosupreme.utils.Formatter.EEEE
    )
}

fun Context.shareLink(url: String?) {
    val sharingIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    //sharingIntent.type = "text/plain"
    if (sharingIntent.resolveActivity(packageManager) != null)
        startActivity(sharingIntent)
}

fun Fragment.openInWeb(url: String) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(url)
    startActivity(intent)
}

fun Fragment.toColor(@ColorRes color: Int): Int {
    return getColor(this.requireContext(), color)
}


fun Fragment.toColorDrawable(@ColorRes color: Int): ColorDrawable {
    return ColorDrawable(toColor(color))
}


fun AppCompatActivity?.setAdjustPan() {
    this?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN or WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
}


fun AppCompatCheckBox.setDrawableColor(@ColorRes color: Int) {
    compoundDrawables.filterNotNull().forEach {
        it.colorFilter = PorterDuffColorFilter(getColor(context, color), PorterDuff.Mode.SRC_IN)
    }
}


fun Fragment?.setAdjustPan() {
    this?.activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN or WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
}

fun Fragment?.setAdjustResize() {
    this?.activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE or WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
}

fun Fragment?.setAdjustNothing() {
    this?.activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
}

fun Fragment?.setAdjustHidden() {
    this?.activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
}

fun Fragment?.setUnspecified() {
    this?.activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED)
}


fun AppCompatActivity?.setUnspecified() {
    this?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED)
}


/**
 * Returns local ip address of this device
 */
fun getIpAddress(): String {
    try {
        val en = NetworkInterface.getNetworkInterfaces()
        while (en.hasMoreElements()) {
            val intf = en.nextElement()
            val enumIpAddr = intf.inetAddresses
            while (enumIpAddr.hasMoreElements()) {
                val inetAddress = enumIpAddr.nextElement()
                if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address) {
                    return inetAddress.getHostAddress()
                }
            }
        }
    } catch (ex: SocketException) {
        ex.printStackTrace()
    }

    return ""
}


/*fun TextView?.setPrice(string: String){
    this.text=resources.getString(R.string.price_formate)
}*/

fun AppCompatTextView.calculatTime(second: Int?) {

    val totalSeconds = second?.toLong()
    val day = TimeUnit.SECONDS.toDays(totalSeconds!!).toInt()
    val hours = TimeUnit.SECONDS.toHours(totalSeconds) - (day * 24)
    val minute =
        TimeUnit.SECONDS.toMinutes(totalSeconds) - (TimeUnit.SECONDS.toHours(totalSeconds) * 60)
    val second =
        TimeUnit.SECONDS.toSeconds(totalSeconds) - (TimeUnit.SECONDS.toMinutes(totalSeconds) * 60)

    this.text =/* "$hours : $minute : $second"*/
        String.format("%02d:%02d:%02d", hours, minute, second)

}


/**
 * Return pseudo unique ID
 * @return ID
 */
fun getUniquePsuedoID(): String? { // If all else fails, if the user does have lower than API 9 (lower
// than Gingerbread), has reset their device or 'Secure.ANDROID_ID'
// returns 'null', then simply the ID returned will be solely based
// off their Android device information. This is where the collisions
// can happen.
// Thanks http://www.pocketmagic.net/?p=1662!
// Try not to use DISPLAY, HOST or ID - these items could change.
// If there are collisions, there will be overlapping data
    val m_szDevIDShort =
        "35" + Build.BOARD.length % 10 + Build.BRAND.length % 10 + Build.CPU_ABI.length % 10 + Build.DEVICE.length % 10 + Build.MANUFACTURER.length % 10 + Build.MODEL.length % 10 + Build.PRODUCT.length % 10
    // Thanks to @Roman SL!
// https://stackoverflow.com/a/4789483/950427
// Only devices with API >= 9 have android.os.Build.SERIAL
// http://developer.android.com/reference/android/os/Build.html#SERIAL
// If a user upgrades software or roots their device, there will be a duplicate entry
    var serial: String? = null
    try {
        serial = Build::class.java.getField("SERIAL")[null].toString()
        // Go ahead and return the serial for api => 9
        return UUID(m_szDevIDShort.hashCode().toLong(), serial.hashCode().toLong()).toString()
    } catch (exception: Exception) { // String needs to be initialized
        serial = "serial" // some value
    }
    // Thanks @Joe!
// https://stackoverflow.com/a/2853253/950427
// Finally, combine the values we have found by using the UUID class to create a unique identifier
    return UUID(m_szDevIDShort.hashCode().toLong(), serial.hashCode().toLong()).toString()
}

/*fun Location?.toLatLng(): LatLng? {
    return if (this == null)
        null
    else LatLng(this.latitude, this.longitude)
}*/
/*

fun Menu.addToolbarImage(context: Context, layoutInflater: LayoutInflater, @DrawableRes image: Int, scaleType: ImageView.ScaleType = ImageView.ScaleType.CENTER): ImageView {
    val view = layoutInflater.inflate(R.layout.raw_toolbar_image, null)
    val imageViewCall: ImageView = view.findViewById(R.id.imageView)
    imageViewCall.id = R.id.imageView
    imageViewCall.scaleType = scaleType

    val outValue = TypedValue()
    context.getTheme().resolveAttribute(R.attr.selectableItemBackgroundBorderless, outValue, true)
    imageViewCall.setBackgroundResource(outValue.resourceId)
    imageViewCall.setImageResource(image)

    add(0, 0, 0, "")
            .setActionView(view)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
    return imageViewCall
}
*/

/*fun Menu.addToolbarText(context: Context, layoutInflater: LayoutInflater, @StringRes text: Int): TextView {
    val view = layoutInflater.inflate(R.layout.raw_toolbar_text, null)
    val textView: TextView = view.findViewById(R.id.textView)
    textView.id = R.id.textView

    val outValue = TypedValue()
    context.getTheme().resolveAttribute(R.attr.selectableItemBackgroundBorderless, outValue, true)
    textView.setBackgroundResource(outValue.resourceId)
    textView.text = view.context.getString(text)

    add(0, 0, 0, "")
            .setActionView(view)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
    return textView
}*/

/**
 * For Fragments, allows declarations like
 * ```
 * val myViewModel = viewModelProvider(myViewModelFactory)
 * ```
 */


inline fun <reified VM : ViewModel> Fragment.viewModelProvider(
    provider: ViewModelProvider.Factory
) =
    ViewModelProviders.of(this, provider).get(VM::class.java)

/**
 * Like [Fragment.viewModelProvider] for Fragments that want a [ViewModel] scoped to the Activity.
 */
inline fun <reified VM : ViewModel> Fragment.activityViewModelProvider(
    provider: ViewModelProvider.Factory
) =
    ViewModelProviders.of(requireActivity(), provider).get(VM::class.java)

/**
 * Like [Fragment.viewModelProvider] for Fragments that want a [ViewModel] scoped to the parent
 * Fragment.
 */
inline fun <reified VM : ViewModel> Fragment.parentViewModelProvider(
    provider: ViewModelProvider.Factory
) =
    ViewModelProviders.of(parentFragment!!, provider).get(VM::class.java)

/**
 * Like [Fragment.viewModelProvider] for Fragments that want a [ViewModel] scoped to the Activity.
 */
inline fun <reified VM : ViewModel> FragmentActivity.activityViewModelProvider(
    provider: ViewModelProvider.Factory
) =
    ViewModelProviders.of(this, provider).get(VM::class.java)

/**
 * Like [Fragment.viewModelProvider] for Fragments that want a [ViewModel] scoped to the Activity.
 */
inline fun <reified VM : ViewModel> FragmentActivity.viewModelProvider(
    provider: ViewModelProvider.Factory
) =
    ViewModelProviders.of(this, provider).get(VM::class.java)


/**
 * For Dialog Fragments, allows declarations like
 * ```
 * val myViewModel = viewModelProvider(myViewModelFactory)
 * ```
 */
inline fun <reified VM : ViewModel> DialogFragment.viewModelProvider(
    provider: ViewModelProvider.Factory
) =
    ViewModelProviders.of(this, provider).get(VM::class.java)
