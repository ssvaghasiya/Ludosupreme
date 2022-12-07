package com.ludosupreme.extenstions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.button.MaterialButton
import com.tapadoo.alerter.Alerter
import com.ludosupreme.R
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


fun getTxt(view: View): String {
    return when (view) {
        is AppCompatEditText -> view.text!!.toString().trim { it <= ' ' }
        is AppCompatTextView -> view.text.toString().trim { it <= ' ' }
        is AppCompatButton -> view.text.toString().trim { it <= ' ' }
        else -> ""
    }
}

fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
    val bytes = ByteArrayOutputStream()
    inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
    val path = MediaStore.Images.Media.insertImage(
        inContext.contentResolver,
        inImage,
        "IMG_" + Calendar.getInstance().time,
        null
    )
    return Uri.parse(path)
}

/**
 * Show Keyboard.
 */
fun Fragment.showKeyboard() {
    val view = this.activity?.currentFocus
    if (view != null) {
        val inputManager =
            this.activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }
}

/**
 * Hide Keyboard.
 */
fun Fragment.hideKeyboard() {
    val view = this.requireActivity().currentFocus
    if (view != null) {
        val inputManager =
            this.activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    /*  val imm = requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
      imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)*/
}

fun Fragment.clearStack() {
    var count = requireActivity().supportFragmentManager.backStackEntryCount
    while (count > 0) {
        count--
        requireActivity().supportFragmentManager.popBackStack()
    }
}

fun Fragment.goBack() {
    activity?.onBackPressed()
}

inline fun <reified A : Class<*>> Fragment.finishAndStartNewActivity(
    activity: FragmentActivity,
    newActivity: A
) {
    val i = Intent(activity, newActivity)
    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    startActivity(i)
    activity.finish()
}

fun Fragment.finishActivityWithResult(activity: FragmentActivity, bundle: Bundle) {
    val resultIntent = Intent()
    resultIntent.putExtras(bundle)
    activity.setResult(Activity.RESULT_OK, resultIntent)
    activity.finish()
}

fun Fragment.finishActivityWithMessage(message: String) {
    requireActivity().let {
        val resultIntent = Intent()
        val b = Bundle()
        b.putString("message", message)
        resultIntent.putExtras(b)
        it.setResult(Activity.RESULT_OK, resultIntent)
        it.finish()
    }
}

fun Fragment.showLocationInMap(latitude: Double, longitude: Double) {
    val gmmIntentUri = Uri.parse("geo:$latitude,$longitude?z=20")
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
    mapIntent.setPackage("com.google.android.apps.maps")
    if (mapIntent.resolveActivity(requireActivity().packageManager) != null) {
        startActivity(mapIntent)
    }
}

fun Fragment.showPathInMap(latitude: Double, longitude: Double) {
    val gmmIntentUri = Uri.parse("http://maps.google.com/maps?daddr=$latitude,$longitude")
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
    startActivity(mapIntent)
}

fun Context.call(phone: String) {
    val callIntent = Intent(Intent.ACTION_DIAL)
    callIntent.data = Uri.parse("tel:$phone")
    if (callIntent.resolveActivity(packageManager) != null) {
        startActivity(callIntent)
    } else {
        Toast.makeText(this, "Call functionality not available!", Toast.LENGTH_SHORT).show()
    }
}

fun Fragment.showErrorMessage(message: String?) {
    message?.let {
        Alerter.create(requireActivity())
            .setTitle(it)
            .setBackgroundColorRes(R.color.green)
            .setTextTypeface(ResourcesCompat.getFont(requireContext(), R.font.poppins_bold)!!)
            .setDuration(2000)
            .hideIcon()
            .show()
    }
}

/*fun Fragment.getCountryCode(): Country? {
    val listCountry = Country.readJsonOfCountries(requireActivity())
    for (locale in listCountry) {
        if (locale.nameShort.equals(App.country)) {
            return locale
        }
    }
    return null
}*/

fun Fragment.makeStatusBarTransparentDark() {
    requireActivity().window.apply {
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

fun Fragment.makeStatusBarTransparentLight() {
    requireActivity().window.apply {
        clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        } else {
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        statusBarColor = Color.TRANSPARENT
    }
    /* if(appBarLayout.visibility==View.VISIBLE){
             appBarLayout.setOnApplyWindowInsetsListener { v, insets ->
             v.setMarginTop(insets.systemWindowInsetTop)
             insets
         }
     }else{
         placeHolder.setOnApplyWindowInsetsListener { v, insets ->
             v.setMarginTop(insets.systemWindowInsetTop)
             insets
         }
     }*/
    /* coordinatorLayout.setOnApplyWindowInsetsListener { v, insets ->
         v.setMarginTop(insets.systemWindowInsetTop)
         insets
     }*/
}


fun View.setMarginTop(marginTop: Int?) {
    val menuLayoutParams = this.layoutParams as ViewGroup.MarginLayoutParams
    menuLayoutParams.setMargins(0, marginTop!!, 0, 0)
    this.layoutParams = menuLayoutParams
}

/*fun Activity.updateStatusBarColor(color: Int) {
    val window: Window = window
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = ContextCompat.getColor(this, color)
}*/

fun Fragment.updateStatusBarColor(color: Int) {
    val window: Window = requireActivity().window
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = ContextCompat.getColor(requireContext(), color)
}

fun Fragment.clearFrag() {
    requireActivity().window.apply {
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

/*
fun Fragment.bitmapDescriptorFromVector(vectorResId: Int): BitmapDescriptor? {
    val vectorDrawable = ContextCompat.getDrawable(requireContext(), vectorResId)
    vectorDrawable!!.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
    val bitmap = Bitmap.createBitmap(vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    vectorDrawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}

fun Fragment.bitmapDescriptorFromVectorMap(vectorResId: Int): BitmapDescriptor? {
    val vectorDrawable = ContextCompat.getDrawable(requireContext(), vectorResId)
    vectorDrawable!!.setBounds(0, 0, 50, 50)
    val bitmap = Bitmap.createBitmap(vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    vectorDrawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}

fun MaterialButton.changeTintColor(context:Context){
    setTextColor(ContextCompat.getColor(context,R.color.colorWhite))
    backgroundTintList = ContextCompat.getColorStateList(context, R.color.colorGoogleRed)
}


fun Fragment.getBitmap(image: Int): BitmapDescriptor? {
    val height = 50
    val width = 50
    val bitmapDraw = ContextCompat.getDrawable(requireContext(), image) as BitmapDrawable
    val b = bitmapDraw.bitmap
    val bitMap = Bitmap.createScaledBitmap(b, width, height, false)
    return BitmapDescriptorFactory.fromBitmap(bitMap)
}

*/

fun getMonthNameString(month: Int): String {
    return when (month) {
        1 -> "January"
        2 -> "February"
        3 -> "March"
        4 -> "April"
        5 -> "May"
        6 -> "June"
        7 -> "June"
        8 -> "August"
        9 -> "September"
        10 -> "October"
        11 -> "November"
        12 -> "December"
        else -> ""
    }
}


fun Activity.updateStatusBarColor(color: Int?) {

    val window: Window = window

    window.run {
        if (color == R.color.white || color == R.color.colorPrimary)
            getWindow().decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        else
            getWindow().decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE

        //clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    }
    window.statusBarColor = ContextCompat.getColor(this, color!!)
}

fun Activity.fullScreenStatusBar(color: Int?) {
    val window: Window = window

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    window.run {
        if (color == R.color.white || color == R.color.colorPrimary)
            getWindow().decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        else
            getWindow().decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE

        //clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    }
    window.statusBarColor = ContextCompat.getColor(this, color!!)
}


fun compressImage(imageFilePath: String, callBack: () -> Unit) {
    val imageFile = File(imageFilePath)
    val bmOptions = BitmapFactory.Options()
    val decodeBitmap = BitmapFactory.decodeFile(imageFilePath, bmOptions)
    val bitmap = Bitmap.createBitmap(decodeBitmap)

    if (bitmap != null) {
        try {
            FileOutputStream(imageFile).use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, out)
                callBack()
            }
        } catch (e: IOException) {
            e.printStackTrace()

        }

    }
}

fun AppCompatImageView.loadImage(loadImage: Any?) {
    Glide.with(context).load(loadImage)
        .error(R.drawable.image_broken)
        .into(this)
}

fun AppCompatImageView.loadImageWithPlaceHolder(loadImage: Any?) {
    Glide.with(context).load(loadImage)
        .apply(RequestOptions().placeholder(R.drawable.image_placeholder))
        .error(R.drawable.image_broken)
        .centerCrop().into(this)
}

fun AppCompatImageView.loadImageCenterCrop(loadImage: Any?) {
    Glide.with(context).load(loadImage).apply(
        RequestOptions()
            .placeholder(R.drawable.ic_dummy_profile)
    )
        .error(R.drawable.image_broken)
        .centerCrop().into(this)
}


fun AppCompatImageView.loadImageCircleCrop(loadImage: Any?) {
    Glide.with(context).load(loadImage)
        .apply(RequestOptions().placeholder(R.drawable.ic_dummy_profile))
        .apply(RequestOptions.bitmapTransform(CircleCrop()))
        .error(R.drawable.ic_dummy_profile)
        .into(this)
}

fun AppCompatImageView.setImage(context: Context, image: String) {
    Glide
        .with(context)
        .load(image)
        .error(R.drawable.image_broken)
        .centerCrop()
        .into(this)
}


fun AppCompatImageView.loadImageRoundedCorner(loadImage: Any?, corner: Int) {
    Glide.with(context).load(loadImage).transform(CenterCrop(), RoundedCorners(corner))
        .apply(RequestOptions.placeholderOf(R.drawable.ic_dummy_profile))
        .error(R.drawable.image_broken)
        .into(this)
}

fun AppCompatImageView.loadImageRoundedCornerWithPlaceHolder(loadImage: Any?, corner: Int) {
    Glide.with(context).load(loadImage).transform(CenterCrop(), RoundedCorners(corner))
        .error(R.drawable.image_broken)
        .into(this)
}

fun AppCompatImageView.loadImageRoundedCornerWithPlaceHolder(loadImage: Any?) {
    Glide.with(context).load(loadImage)
        .apply(RequestOptions().placeholder(R.drawable.ic_dummy_profile))
        .apply(RequestOptions.bitmapTransform(CircleCrop()))
        .into(this)
}


fun getFileName(): String {
    return Random().nextInt(kotlin.math.abs(System.currentTimeMillis().toInt())).toString() + ".jpg"
}


fun Fragment.setAdjustResize() {
    activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE or WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
}


fun Fragment.setAdjustPan() {
    activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN or WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
}


fun EditText.enableSearch(textWatcher: TextWatcher) {
    if (!this.hasFocus()) {
        this.isCursorVisible = true
        this.isFocusable = true
        this.isFocusableInTouchMode = true
        this.requestFocus()

        this.addTextChangedListener(textWatcher)

    }
}

fun EditText.disableSearch(textWatcher: TextWatcher) {
    if (this.hasFocus()) {
        this.isCursorVisible = false
        this.isFocusable = false
        this.isFocusableInTouchMode = false
        this.requestFocus()

        this.removeTextChangedListener(textWatcher)

    }
}

/**
 * Button Change Background Tine.
 */
fun MaterialButton.changeTint(color: Int) {
    this.backgroundTintList = ContextCompat.getColorStateList(context, color)
}

/**
 *Change Text color
 */

fun View.changeTextColor(color: Int) {
    when (this) {
        this as AppCompatTextView -> {
            setTextColor(ContextCompat.getColor(context, color))
        }

    }

}




