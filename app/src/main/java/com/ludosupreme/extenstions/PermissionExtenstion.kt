package com.ludosupreme.extenstions

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.fragment.app.Fragment
import com.fondesa.kpermissions.extension.*

import com.google.android.gms.maps.model.LatLng
import com.ludosupreme.R
import com.ludosupreme.ui.base.BaseFragment
import com.ludosupreme.utils.LocationManager


/*fun Fragment.openImagePicker(callback: (String) -> Unit) {
    val request = permissionsBuilder(Manifest.permission.WRITE_EXTERNAL_STORAGE).build()
    request.onAccepted {
        val imageChooserDialog = ImageChooserDialog.newInstance()
        imageChooserDialog.setImageCallBack(object : ImageChooserDialog.ImageCallBack {
            override fun sendImage(imagePicker: ImagePicker) {
                callback(imagePicker.imagePath?:"")
            }
        })
        imageChooserDialog.show(childFragmentManager, "image Picker")
    }.onDenied {

    }.onPermanentlyDenied {
        showErrorMessage("Already denied")

    }.onShouldShowRationale { _, nonce ->
        nonce.use()
    }
    request.send()
}*/


fun BaseFragment.requirePermission(message: String, callback: (LatLng) -> Unit) {
    val request =
        requireActivity().permissionsBuilder(Manifest.permission.ACCESS_FINE_LOCATION).build()
    request.onAccepted {
        val locationManager = LocationManager(requireActivity())
        locationManager.activity = requireActivity()
        locationManager.fetchLatestLocation { location, exception ->
            if (location != null) {
                callback(LatLng(location.latitude, location.longitude))
            } else {
                exception?.message?.let { it1 -> showErrorMessage(it1) }
            }
        }

    }.onDenied {

    }.onPermanentlyDenied {
        commandDialogYesNo(getString(R.string.app_name), message) {
            if (it == 1) {
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", requireActivity().packageName, null)
                intent.data = uri
                requireActivity().startActivity(intent)
                requireActivity().finishAffinity()
            } else {
                requireActivity().finishAffinity()
            }

        }

    }.onShouldShowRationale { _, nonce ->
        nonce.use()
    }
    request.send()
}

fun BaseFragment.requirePermission(message: String, callback: () -> Unit) {
    val request =
        requireActivity().permissionsBuilder(Manifest.permission.WRITE_EXTERNAL_STORAGE).build()
    request.onAccepted {
        callback()

    }.onDenied {

    }.onPermanentlyDenied {
        commandDialogYesNo(getString(R.string.app_name), message) {
            if (it == 1) {
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", requireActivity().packageName, null)
                intent.data = uri
                requireActivity().startActivity(intent)
                requireActivity().finishAffinity()
            } else {
                requireActivity().finishAffinity()
            }
        }

    }.onShouldShowRationale { _, nonce ->
        nonce.use()
    }
    request.send()
}

fun Fragment.requirePermission(message: String, permission: String, callback: () -> Unit) {
    val request = this.permissionsBuilder(permission).build()
    request.onAccepted {
        callback()
    }.onDenied {

    }.onPermanentlyDenied {
        commandDialogYesNo(getString(R.string.app_name), message) {
            if (it == 1) {
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", requireActivity().packageName, null)
                intent.data = uri
                requireActivity().startActivity(intent)
                requireActivity().finishAffinity()
            } else {
                requireActivity().finishAffinity()
            }

        }

    }.onShouldShowRationale { _, nonce ->
        nonce.use()
    }
    request.send()
}