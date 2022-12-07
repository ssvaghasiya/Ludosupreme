package com.ludosupreme.utils

import android.content.res.Resources
import android.util.Log
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.ObjectMetadata
import com.ludosupreme.R
import com.ludosupreme.core.Session
import com.ludosupreme.di.App
import com.ludosupreme.extenstions.isInternetConnected
import com.ludosupreme.ui.base.BaseFragment
import java.io.File
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


class S3Manager @Inject constructor(
    private val transferUtility: TransferUtility,
    private val resources: Resources
) {


    //Here Add Your Bucket
    companion object {
        var BUCKET = "parth-bucket-hlis"
        var MAIN_FOLDER = App.session?.credential?.name
        var aWSAccessKeyId: String? = App.session?.credential?.accessKey
        var aWSSecretKey: String? = App.session?.credential?.secretKey

        var aWSRegion: String? = App.session?.credential?.region
//        var aWSRegion: String? = "me-south-1"
    }

    //Here add your project directory
    enum class S3Dir(val sub: String) {
        Customer(""),
        Hourse(""),
        User(""),
        Transpotation(""),
        Realestate(""),
        Services(""),
        Jobs(""),
        Horses(""),
        Breeding(""),
        Image(""),
        Audio("");


        override fun toString(): String {
            Debug.e(
                "UploadPath",
                "$MAIN_FOLDER/${super.toString().toLowerCase(Locale.getDefault())}"
            )
//            return "$BUCKET/${super.toString().toLowerCase(Locale.getDefault())}"
            return "$MAIN_FOLDER/${super.toString().toLowerCase(Locale.getDefault())}"
        }
    }

    //Here add your project directory
    private fun provideDir(s3Dir: S3Dir): String {
        return when (s3Dir) {
            S3Dir.Hourse -> BUCKET.toString()
            S3Dir.Customer -> S3Dir.Customer.toString()
            S3Dir.User -> S3Dir.User.toString()
            S3Dir.Transpotation -> S3Dir.Transpotation.toString()
            S3Dir.Realestate -> S3Dir.Realestate.toString()
            S3Dir.Services -> S3Dir.Services.toString()
            S3Dir.Jobs -> S3Dir.Jobs.toString()
            S3Dir.Horses -> S3Dir.Horses.toString()
            S3Dir.Breeding -> S3Dir.Breeding.toString()
            S3Dir.Image -> S3Dir.Image.toString()
            S3Dir.Audio -> S3Dir.Audio.toString()
//            S3Dir.SubDirUserNew ->S3Dir.SubDirUserNew.sub
        }
    }


    //For single upload
    fun upload(fileToUpload: File, s3Dir: S3Dir, uploadingStatus: UploadingStatus) {
//        val profileName = "default.jpg"
        val profileName = fileToUpload.name

        val transferListener = object : TransferListener {
            override fun onProgressChanged(id: Int, bytesCurrent: Long, bytesTotal: Long) {
                uploadingStatus.onProgress((bytesCurrent / bytesTotal * 100).toInt())
            }

            override fun onStateChanged(id: Int, transferState: TransferState) {
                if (transferState == TransferState.COMPLETED) {
                    Debug.e("Image Url:::::$profileName")
                    uploadingStatus.onCompleted(profileName)

                } else if (transferState in arrayOf(
                        TransferState.FAILED,
                        TransferState.WAITING_FOR_NETWORK,
                        TransferState.UNKNOWN
                    )
                ) {
                    Debug.e("onStateChanged:::::Image upload failed:::::${transferState.name}")
                    uploadingStatus.onFiled(
                        resources.getString(
                            R.string.message_image_upload_failed,
                            transferState.name
                        )
                    )

                }
            }

            override fun onError(id: Int, ex: Exception?) {
                Debug.e("onError:::::Image upload failed Error :- ${ex.toString()}")
                uploadingStatus.onFiled(
                    resources.getString(
                        R.string.message_image_upload_failed,
                        ex.toString()
                    )
                )

            }
        }

        //start file transfer from here
        addTransfer(
            fileToUpload,
            profileName,
            provideDir(s3Dir).apply {
                Log.e("Path", this)
            },
            transferListener
        )

    }

    //For multipleUpload upload
    fun uploadsMultiple(
        filesToUpload: List<File>,
        s3Dir: S3Dir,
        uploadingStatus: UploadingStatus,
        context: BaseFragment
    ) {

        if (!isInternetConnected(context.requireContext())) {
            context.showMessage(resources.getString(R.string.connect_to_internet))
            context.showLoader(false)
            return
        }

        val imagesData = ArrayList<String>()
        val finalImagesData = ArrayList<String>()

        for (fileToUpload in filesToUpload) {
            val fileName = fileToUpload.name

            finalImagesData.add(fileName)

            val transferListener = object : TransferListener {
                override fun onProgressChanged(id: Int, bytesCurrent: Long, bytesTotal: Long) {
                    uploadingStatus.onProgress((bytesCurrent / bytesTotal * 100).toInt())
                }

                override fun onStateChanged(id: Int, transferState: TransferState) {
                    if (transferState == TransferState.COMPLETED) {
                        Debug.e("Image Url:::::$fileName")

                        imagesData.add(fileName)

                        if (filesToUpload.size == imagesData.size) {
                            uploadingStatus.onCompleted(finalImagesData)
                        }

                        uploadingStatus.filesUploadedCount(
                            filesToUpload.size,
                            (filesToUpload.size - imagesData.size)
                        )

                    } else if (transferState in arrayOf(
                            TransferState.FAILED,
                            TransferState.WAITING_FOR_NETWORK,
                            TransferState.UNKNOWN
                        )
                    ) {
                        Debug.e("onStateChanged:::::Image upload failed:::::${transferState.name}")
                        uploadingStatus.onFiled(
                            resources.getString(
                                R.string.message_image_upload_failed,
                                transferState.name
                            )
                        )

                    }
                }


                override fun onError(id: Int, ex: Exception?) {
                    Debug.e("onError:::::Image upload failed Error :- ${ex.toString()}")
                    uploadingStatus.onFiled(
                        resources.getString(
                            R.string.message_image_upload_failed,
                            ex.toString()
                        )
                    )

                }
            }

            //start file transfer from here
            addTransfer(
                fileToUpload,
                fileName,
                provideDir(s3Dir),
                transferListener
            )
        }
    }

    private fun addTransfer(
        fileToUpload: File,
        fileName: String,
        bucketWithFolder: String,
        transferListener: TransferListener
    ) {
        transferUtility.upload(
            bucketWithFolder,
            fileName,
            fileToUpload,
            ObjectMetadata(),
            CannedAccessControlList.PublicReadWrite,
            transferListener
        )
    }

    interface UploadingStatus {
        fun onCompleted(fileName: String)
        fun onCompleted(fileNameArrayList: ArrayList<String>) {}
        fun filesUploadedCount(totalFiles: Int, pendingFile: Int) {}
        fun onFiled(error: String)
        fun onProgress(progress: Int) {}
    }
}