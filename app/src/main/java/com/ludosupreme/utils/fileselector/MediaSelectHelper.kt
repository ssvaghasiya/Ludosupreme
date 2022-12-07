package com.ludosupreme.utils.fileselector


import android.Manifest
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.ludosupreme.R
import com.ludosupreme.ui.base.BaseActivity
import com.ludosupreme.utils.Constant
import com.ludosupreme.utils.Constant.CAMERA_REQUEST
import com.ludosupreme.utils.Constant.GALLERY_REQUEST
import com.ludosupreme.utils.Constant.GALLERY_VIDEO_REQUEST
import com.ludosupreme.utils.Constant.MY_PERMISSIONS_REQUEST_OPEN_CAMERA
import com.ludosupreme.utils.Constant.MY_PERMISSIONS_REQUEST_OPEN_GALLERY
import com.ludosupreme.utils.Constant.VIDEO_REQUEST
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


class MediaSelectHelper(
    private val mFragment: Fragment, private val mMediaSelector: MediaSelector,
    internal var activity: BaseActivity
) {
    var mCurrentPhotoPath: String = ""
    internal var photoFile: File? = null
    private val RESULT_CROP = 501
    private val cameraIntent: Intent? = null
    private var galleryIntent: Intent? = null
    private val uriArrayList: java.util.ArrayList<Uri>? = null
    private val permission = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA
    )

    private var cropType = Constant.CropSquare
    private var isCrop = true
    private var openVideo = false

    init {
        galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        galleryIntent!!.type = "image/*"
    }

    /**
     * Open camera to click image
     */
    fun openCamera() {

        if (Build.VERSION.SDK_INT >= 23 &&
            (ContextCompat.checkSelfPermission(
                mFragment.requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(
                        mFragment.requireContext(),
                        Manifest.permission.CAMERA
                    ) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(
                        mFragment.requireContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED)
        ) {

            mFragment.requestPermissions(
                permission,
                MY_PERMISSIONS_REQUEST_OPEN_CAMERA
            )
        } else {
            if (openVideo) {
                dispatchTakeVideoIntent()
            } else {
                dispatchTakePictureIntent()
            }
        }

    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // Ensure that there's a camera activity to handle the intent
        try {
//            if (takePictureIntent.resolveActivity(mFragment.requireContext().packageManager) != null) {
            // Create the File where the photo should go

            try {
                photoFile = createImageFile()
            } catch (ex: IOException) {
                // Error occurred while creating the File
                ex.printStackTrace()

            }

            // Continue only if the File was successfully created
            if (photoFile != null && mCurrentPhotoPath != null) {
                val photoURI = FileProvider.getUriForFile(
                    mFragment.requireContext(),
                    "${mFragment.requireContext().packageName}.provider",
                    photoFile!!
                )

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                mFragment.startActivityForResult(takePictureIntent, CAMERA_REQUEST)
            }
//            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    var mediaFile: File? = null

    private fun dispatchTakeVideoIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        // Ensure that there's a camera activity to handle the intent
        try {
//            if (takePictureIntent.resolveActivity(mFragment.requireContext().packageManager) != null) {
            // Create the File where the photo should go

            try {
                photoFile = createVideoFile()
            } catch (ex: IOException) {
                // Error occurred while creating the File
                ex.printStackTrace()

            }


            // Continue only if the File was successfully created
            if (photoFile != null && mCurrentPhotoPath != null) {
                val photoURI = FileProvider.getUriForFile(
                    mFragment.requireContext(),
                    "${mFragment.requireContext().packageName}.provider",
                    photoFile!!
                )

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                mFragment.startActivityForResult(takePictureIntent, VIDEO_REQUEST)
            }
//            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Open gallery for select single image
     */
    fun openGallery() {

        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(
                mFragment.requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            askReadWritePermission()
        } else {
            if (openVideo) {
                galleryIntent = Intent(Intent.ACTION_GET_CONTENT)
                galleryIntent?.type = "video/*"
                mFragment.startActivityForResult(galleryIntent, GALLERY_VIDEO_REQUEST)
            } else {
                mFragment.startActivityForResult(galleryIntent, GALLERY_REQUEST)
            }
        }
    }

    private fun askReadWritePermission() {

        mFragment.requestPermissions(
            permission,
            MY_PERMISSIONS_REQUEST_OPEN_GALLERY
        )
    }

    fun canSelectMultiple(canSelect: Boolean) {
        if (canSelect) {
            galleryIntent = Intent(Intent.ACTION_GET_CONTENT)
            galleryIntent!!.type = "*/*"
            galleryIntent!!.putExtra(
                Intent.EXTRA_MIME_TYPES,
                arrayOf("image/jpeg", "image/png", "video/mp4")
            )

            galleryIntent!!.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        } else {
            galleryIntent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            galleryIntent!!.type = "image/*"
        }
    }

    private fun getImageUri(inContext: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path =
            MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }

    protected fun getRotetdBitmap(bitmap: Bitmap, photoPath: String): Bitmap? {
        val orientation: Int
        var rotatedBitmap: Bitmap? = null
        var ei: ExifInterface? = null
        try {
            ei = ExifInterface(photoPath)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        if (ei != null) {
            orientation = ei.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED
            )
        } else {
            return bitmap
        }

        when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotatedBitmap = rotateImage(bitmap, 90f)

            ExifInterface.ORIENTATION_ROTATE_180 -> rotatedBitmap = rotateImage(bitmap, 180f)

            ExifInterface.ORIENTATION_ROTATE_270 -> rotatedBitmap = rotateImage(bitmap, 270f)


            ExifInterface.ORIENTATION_NORMAL -> rotatedBitmap = bitmap
            else -> rotatedBitmap = rotateImage(bitmap, 90f)
        }
        return rotatedBitmap
    }

    protected fun rotateImage(source: Bitmap, angle: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(
            source, 0, 0, source.width, source.height,
            matrix, true
        )
    }

    /**
     * After click image from camera or
     * select image from gallery are shown here
     *
     * @param requestCode request code
     * @param resultCode  result code
     * @param data        data
     */

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        val selectedMedia: Uri?
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            Log.e("Hiiii", "*****")
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                val resultUri = result.uri
                val imageStream: InputStream?
                try {
                    imageStream = activity.contentResolver.openInputStream(resultUri)
                    val selectedImage = BitmapFactory.decodeStream(imageStream)
//                    val Base64 = encodeImage(selectedImage)
                    val path = resultUri.path
                    /*onImageChosenResponse(path, Base64)
                    onImageUri()*/
                    mMediaSelector.onImageUri(resultUri)

                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                result.error
            }
        } else if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {

            val file = File(mCurrentPhotoPath)
            if (Build.MANUFACTURER.equals("samsung")) {
                val bitmap = getRotetdBitmap(BitmapFactory.decodeFile(file.path), file.path)
                if (bitmap != null) {
                    try {
                        FileOutputStream(file).use { out ->
                            bitmap.compress(
                                Bitmap.CompressFormat.PNG,
                                50,
                                out
                            ) // bmp is your Bitmap instance
                            // PNG is a lossless format, the compression factor (100) is ignored
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                        Log.e(TAG, e.message ?: "")
                    }

                    openCropView(Uri.fromFile(file))
                }
            } else {
                openCropView(Uri.fromFile(file))
            }

        } else if (data != null && requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            val retriever = MediaMetadataRetriever()

            selectedMedia = data.data
            val cR = mFragment.requireContext().contentResolver
            val mime = MimeTypeMap.getSingleton()
            val type = mime.getExtensionFromMimeType(cR.getType(selectedMedia!!))

            if (Objects.requireNonNull(type).equals("png", ignoreCase = true) || type!!.equals(
                    "jpeg",
                    ignoreCase = true
                ) || type.equals("jpg", ignoreCase = true)
            ) {
                /*mMediaSelector.onImageUri(selectedMedia);*/
                if (selectedMedia.toString().contains("image")) {
                    // cropImage(getRealPathFromURI(selectedMedia));
                    openCropView(selectedMedia)
                } else {
                    //downloadImageFromPhotos(selectedMedia)

                    openCropView(selectedMedia)

                }
                //performCrop(selectedMedia);
            }


            retriever.release()
        } else if (data != null && requestCode == Constant.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data.extras != null) {
                if (data.extras!!.getString("ImageData") != null && !data.extras!!.getString("ImageData")!!
                        .isEmpty()
                ) {
                    mMediaSelector.onImageUri(Uri.parse(data.extras!!.getString("ImageData")))
                }
            }

        } else if (requestCode == VIDEO_REQUEST) {
            if (resultCode == RESULT_OK) {
                selectedMedia = data?.data
                val file: File? = File(mCurrentPhotoPath)

                if (selectedMedia != null) {
                    Log.e("Video", "tmp_mediaFile : " + file?.path!!)
                    mMediaSelector.onImageUri(
                        selectedMedia
                    )
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.e("Video", "Video recording cancelled.")
            } else {
                Log.e("Video", "Failed to record video")
            }
        } else if (data != null && requestCode == GALLERY_VIDEO_REQUEST && resultCode == RESULT_OK) {
            selectedMedia = data.data
            val file: File? = File(mCurrentPhotoPath)

            if (selectedMedia != null) {
                Log.e("Video_Gallery", file?.path!!)
                mMediaSelector.onImageUri(
                    selectedMedia
                )
            }
        }

    }


    private fun openCropView(file: Uri) {
        if (isCrop) {
            when (this.cropType) {
                Constant.CropSquare -> CropImage.activity(file)
                    .setAspectRatio(4, 4)
                    .start(activity)
                Constant.CropRectangle -> CropImage.activity(file)
                    .setAspectRatio(6, 4)
                    .start(activity)
                else -> CropImage.activity(file)
                    .setCropShape(CropImageView.CropShape.OVAL)
                    .setAspectRatio(1, 1)
                    .start(activity)
            }

        } else {
            mMediaSelector.onImageUri(file)
        }
    }

    private fun getRealPathFromURI(contentURI: Uri): String {
        val result: String
        val cursor =
            mFragment.requireContext().contentResolver.query(contentURI, null, null, null, null)
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.path!!
        } else {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            result = cursor.getString(idx)
            cursor.close()
        }
        return result
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {


        val timeStamp = SimpleDateFormat(
            "yyyyMMdd_HHmmss",
            Locale.getDefault()
        ).format(Date())
        val imageFileName = timeStamp
        val storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
            imageFileName, /* prefix */
            ".jpg", /* suffix */
            storageDir      /* directory */
        )
        mCurrentPhotoPath = image.absolutePath
        return image
    }

    private fun createVideoFile(): File {
        val timeStamp = SimpleDateFormat(
            "yyyyMMdd_HHmmss",
            Locale.getDefault()
        ).format(Date())
        val imageFileName = timeStamp
        val storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
            imageFileName, /* prefix */
            ".mp4", /* suffix */
            storageDir      /* directory */
        )
        mCurrentPhotoPath = image.absolutePath
        return image
    }

    fun getImageFromUri(selectedImage: Uri): Bitmap? {
        var photo: Bitmap? = null
        var stream: ByteArrayOutputStream? = null
        stream = ByteArrayOutputStream()
        photo = decodeUri(selectedImage)
        photo?.compress(Bitmap.CompressFormat.PNG, 0, stream)

        return photo
    }

    /**
     * Permission request result handle here
     *
     * @param requestCode  request code
     * @param permissions  permissions
     * @param grantResults grant results
     */
    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {

        if (grantResults.isNotEmpty()) {
            when (requestCode) {
                MY_PERMISSIONS_REQUEST_OPEN_CAMERA -> if (checkAllPermission(grantResults)) {
                    openCamera()
                } else if (!checkAllPermission(grantResults)) {
                    if (!mFragment.shouldShowRequestPermissionRationale(permissions[0])) {
                        activity.showErrorMessage("Permission denied already")
                        activity.showToast("Please allow permission from setting ")
                    }
                }
                MY_PERMISSIONS_REQUEST_OPEN_GALLERY -> if (checkAllPermission(grantResults)) {
                    openGallery()
                } else if (!checkAllPermission(grantResults)) {
                    if (!mFragment.shouldShowRequestPermissionRationale(permissions[0])) {
                        activity.showErrorMessage("Permission denied already")
                        activity.showToast("Please allow permission from setting ")
                    }
                }
            }
        }
    }

    private fun checkAllPermission(grantResults: IntArray): Boolean {
        for (data in grantResults) {
            if (data != PackageManager.PERMISSION_GRANTED)
                return false
        }
        return true
    }

    /**
     * Manage image memory and resize
     *
     * @param selectedImage imageURI
     * @return bitmap
     * @throws FileNotFoundException throws
     */
    private fun decodeUri(selectedImage: Uri): Bitmap? {
        val o = BitmapFactory.Options()
        o.inJustDecodeBounds = true
        try {
            BitmapFactory.decodeStream(
                mFragment.requireContext().contentResolver.openInputStream(
                    selectedImage
                ), null, o
            )
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }

        val REQUIRED_SIZE = 100

        var width_tmp = o.outWidth
        var height_tmp = o.outHeight
        var scale = 1
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE) {
                break
            }
            width_tmp /= 2
            height_tmp /= 2
            scale *= 2
        }

        val o2 = BitmapFactory.Options()
        o2.inSampleSize = scale
        try {
            return BitmapFactory.decodeStream(
                mFragment.requireContext().contentResolver.openInputStream(selectedImage), null, o2
            )
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            return null
        }

    }

    companion object {
        private val TAG = "MediaSelectHelper"

        fun getRealPathFromURI_API19(context: Context, uri: Uri): String {


            var filePath = ""
            var wholeID: String? = null
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                wholeID = DocumentsContract.getDocumentId(uri)
            }

            // Split at colon, use second item in the array
            val id = wholeID!!.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]

            val column = arrayOf(MediaStore.Images.Media.DATA)

            // where id is equal to
            val sel = MediaStore.Images.Media._ID + "=?"

            val cursor = context.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                column, sel, arrayOf(id), null
            )

            val columnIndex = cursor!!.getColumnIndex(column[0])

            if (cursor.moveToFirst()) {
                filePath = cursor.getString(columnIndex)
            }
            cursor.close()
            return filePath
        }

        val filename: String
            get() {
                val file = File(Environment.getExternalStorageDirectory().path, "puppi/Images")
                if (!file.exists()) {
                    file.mkdirs()
                }
                return file.absolutePath + "/" + System.currentTimeMillis() + ".jpg"
            }

        private fun getMatrix(f: File): Matrix {
            val mat = Matrix()
            try {
                val exif = ExifInterface(f.path)
                val orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, -1
                )
                when (orientation) {
                    ExifInterface.ORIENTATION_ROTATE_180 -> {
                        Log.e("Matrix", "rotation:::::rotate 180")
                        mat.postRotate(180f)
                    }
                    ExifInterface.ORIENTATION_ROTATE_90 -> {
                        Log.e("Matrix", "rotation:::::rotate 90")
                        mat.postRotate(90f)
                    }
                    ExifInterface.ORIENTATION_ROTATE_270 -> {
                        Log.e("Matrix", "rotation:::::rotate 270")
                        mat.postRotate(270f)
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
                Log.e("Matrix", "error in finding exif information")
            }

            return mat
        }

        fun calculateInSampleSize(
            options: BitmapFactory.Options,
            reqWidth: Int,
            reqHeight: Int
        ): Int {
            val height = options.outHeight
            val width = options.outWidth
            var inSampleSize = 1

            if (height > reqHeight || width > reqWidth) {
                val heightRatio = Math.round(height.toFloat() / reqHeight.toFloat())
                val widthRatio = Math.round(width.toFloat() / reqWidth.toFloat())
                inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
            }
            val totalPixels = (width * height).toFloat()
            val totalReqPixelsCap = (reqWidth * reqHeight * 2).toFloat()
            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                inSampleSize++
            }

            return inSampleSize
        }
    }

    fun selectOptionsForImagePicker(
        isCrop1: Boolean,
        cropType: String = Constant.CropSquare,
        openVideo: Boolean? = false
    ) {
        this.cropType = cropType
        this.isCrop = isCrop1
        this.openVideo = openVideo!!
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Choose Image Source")

        var items = arrayOf(
            activity.resources.getString(R.string.label_camera),
            activity.resources.getString(R.string.label_gallery)
        )

        if (this.openVideo) {
            items[0] = activity.resources.getString(R.string.label_video)
        }

        builder.setItems(items) { dialog, which ->


            when (which) {
                0 -> openCamera()
                1 -> openGallery()
            }
        }


        builder.show()
    }


    fun showImageMenu(view: View) {
        val popup = PopupMenu(activity, view)
        popup.menu.add("Camera")
        popup.menu.add("Gallery")
        popup.setOnMenuItemClickListener { item ->

            when (item.title.toString()) {
                "Camera" -> {
                    openCamera()
                }
                "Gallery" -> {
                    openGallery()
                }
            }
            true
        }
        popup.show()


    }

}