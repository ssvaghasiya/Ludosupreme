package com.ludosupreme.utils.fileselector

import android.net.Uri
import java.util.*

interface MediaSelector {
    fun onImageUri(uri: Uri)

    fun onImageUriList(uriArrayList: ArrayList<Uri>)
}
