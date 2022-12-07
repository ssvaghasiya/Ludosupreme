package com.ludosupreme.utils

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.DocumentsContract
import android.provider.MediaStore
import androidx.loader.content.CursorLoader


class RealPathUtil {

    companion object {
        @SuppressLint("NewApi")
        fun getRealPathFromURI_API19(context: Context, uri: Uri): String? {
            var filePath = ""
            return try { // FIXME NPE error when select image from QuickPic, Dropbox etc
                val wholeID = DocumentsContract.getDocumentId(uri)
                // Split at colon, use second item in the array
                val id = wholeID.split(":").toTypedArray()[1]
                val column = arrayOf(MediaStore.Images.Media.DATA)
                // where id is equal to
                val sel = MediaStore.Images.Media._ID + "=?"
                val cursor: Cursor? = context.contentResolver.query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    column, sel, arrayOf(id), null
                )
                val columnIndex: Int = cursor!!.getColumnIndex(column[0])
                if (cursor.moveToFirst()) {
                    filePath = cursor.getString(columnIndex)
                }
                cursor.close()
                filePath
            } catch (e: Exception) { // this is the fix lol
                val result: String?
                val cursor: Cursor? = context.contentResolver.query(uri, null, null, null, null)
                if (cursor == null) { // Source is Dropbox or other similar local file path
                    result = uri.path
                } else {
                    cursor.moveToFirst()
                    val idx: Int = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                    result = cursor.getString(idx)
                    cursor.close()
                }
                result
            }
        }


        @SuppressLint("NewApi")
        fun getRealPathFromURI_API11to18(context: Context?, contentUri: Uri?): String? {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            var result: String? = null
            val cursorLoader = CursorLoader(
                context!!,
                contentUri!!, proj, null, null, null
            )
            val cursor: Cursor? = cursorLoader.loadInBackground()
            if (cursor != null) {
                val column_index: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                cursor.moveToFirst()
                result = cursor.getString(column_index)
            }
            return result
        }

        fun getRealPathFromURI_BelowAPI11(context: Context, contentUri: Uri?): String? {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            val cursor: Cursor? =
                context.contentResolver.query(contentUri!!, proj, null, null, null)
            val column_index: Int = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            return cursor.getString(column_index)
        }
    }
}