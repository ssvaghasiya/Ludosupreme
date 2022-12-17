package com.ludosupreme.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.Build
import android.provider.MediaStore
import android.provider.Settings
import android.text.format.DateFormat
import android.text.format.Formatter
import android.util.Base64
import android.util.Log
import com.ludosupreme.BuildConfig
import com.ludosupreme.R
import com.ludosupreme.core.Session
import com.ludosupreme.data.pojo.ApiRequestData
import java.io.*
import java.net.URL
import java.net.URLConnection
import java.nio.ByteBuffer
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.zip.DeflaterOutputStream


object Utils {
    fun getHashKey(context: Context) {
//        val sha1 = "0f:04:66:e6:ee:a8:27:cb:3a:c9:42:6f:03:1c:14:e0:fd:fe:ec:45"
//        val arr = sha1.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
//        val byteArr = ByteArray(arr.size)
//
//        for (i in arr.indices) {
//            byteArr[i] = Integer.decode("0x" + arr[i]).toByte()
//        }
//
//        Debug.e("KeyHash 2 : ", Base64.encodeToString(byteArr, Base64.NO_WRAP))

        try {
            val info: PackageInfo = context.packageManager.getPackageInfo(
                context.packageName,
                PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Debug.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
    }

    fun displayImage(uri: Uri?, context: Context): Bitmap? {
        try {
            val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
            var realPath: String? = ""
            if (Build.VERSION.SDK_INT < 11) {
                realPath = RealPathUtil.getRealPathFromURI_BelowAPI11(context, uri)
            } else if (Build.VERSION.SDK_INT < 19) {
                realPath = RealPathUtil.getRealPathFromURI_API11to18(context, uri)
            } else {
                realPath = RealPathUtil.getRealPathFromURI_API19(context, uri!!)
            }
            Log.e("TAG001", "realPath: $realPath")
            return bitmap
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    fun getUTCCalendar(): Calendar {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        return calendar
    }

    fun convertAudioToBytes(context: Context?, uri: Uri?): ByteArray? {
        var audioBytes: ByteArray? = null
        try { //  w  w w  . j ava 2s . c  o m
            val baos = ByteArrayOutputStream()
            val fis = FileInputStream(File(uri.toString()))
            val buf = ByteArray(1024)
            var n: Int
            while (-1 != fis.read(buf).also { n = it }) baos.write(buf, 0, n)
            audioBytes = baos.toByteArray()
//            audioBytes = compress(baos.toByteArray())
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return audioBytes
    }

    fun compress(data: ByteArray?): ByteArray? {
        return try {
            val out = ByteArrayOutputStream()
            val defl = DeflaterOutputStream(out)
            defl.write(data)
            defl.flush()
            defl.close()
            out.toByteArray()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
//            exitProcess(150)
            null
        }
    }

    /* @Throws(IOException::class)
     fun getBytesFromUrl(dataUrl: String): ByteArray? {
         try {
             val url = URL(dataUrl)
             val output = ByteArrayOutputStream()
             url.openStream().use { stream ->
                 val buffer = ByteArray(1024)
                 while (true) {
                     val bytesRead: Int = stream.read(buffer)
                     if (bytesRead < 0) {
                         break
                     }
                     output.write(buffer, 0, bytesRead)
                 }
             }
             return output.toByteArray()
         } catch (e: Exception) {
             e.printStackTrace()
         }
         return null
     }*/

    fun getBytesFromUrl(dataUrl: String): ByteArray? {
        try {
            val url = URL(dataUrl)
            val output = ByteArrayOutputStream()
            val conn: URLConnection? = url.openConnection()
            conn?.setRequestProperty("User-Agent", "Firefox")

            conn?.getInputStream()?.use { inputStream ->
                var n = 0
                val buffer = ByteArray(1024)
                while (-1 != inputStream.read(buffer).also { n = it }) {
                    output.write(buffer, 0, n)
                }
            }
            val img = output.toByteArray()
            val imageBytes: ByteBuffer = ByteBuffer.wrap(img)
            return img
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun getDeviceID(context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    fun getIpOfDevice(context: Context): String {
        val wm = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val ip: String = Formatter.formatIpAddress(wm.connectionInfo.ipAddress)
        return ip
    }

    fun getDeviceInfo(context: Context?, session: Session): ApiRequestData {
        Debug.e("DEVICE_TOKEN", session.deviceId)
        return ApiRequestData().apply {
            device_type = Session.DEVICE_TYPE
            device_token = session.deviceId
//            uuid = getDeviceID()
//            ip = getIpOfDevice()
            os_version = Build.VERSION.RELEASE
            model_name = Build.MODEL
            app_version = BuildConfig.VERSION_CODE.toString()
            device_name = Build.BRAND
            build_version_number = BuildConfig.VERSION_NAME
        }
    }


    fun openTimeFragment(context: Context) {
        val mTimePicker: TimePickerDialog
        val c = Calendar.getInstance()
        val hour = c[Calendar.HOUR_OF_DAY]
        val minute = c[Calendar.MINUTE]
        mTimePicker = TimePickerDialog(
            context,
            { timePicker, selectedHour, selectedMinute ->
                val time = "$selectedHour:$selectedMinute"
                val fmt = SimpleDateFormat("HH:mm")
                var date: Date? = null
                try {
                    date = fmt.parse(time)
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
                val fmtOut = SimpleDateFormat("hh:mm aa")
                val formattedTime = fmtOut.format(date)
//                binding.editTextEmail.setText(formattedTime)
            }, hour, minute, false
        ) //No 24 hour time
        mTimePicker.setTitle("Select Time")
        mTimePicker.show()
    }

    fun get12HourTime(time: String): String {
        val fmt = SimpleDateFormat("HH:mm")
        var date: Date? = null
        try {
            date = fmt.parse(time)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val fmtOut = SimpleDateFormat("hh:mm a")
        val formattedTime = fmtOut.format(date)
        return formattedTime
    }


    fun getDuration(file: File): String? {
        val mediaMetadataRetriever = MediaMetadataRetriever()
        mediaMetadataRetriever.setDataSource(file.absolutePath)
        val durationStr =
            mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        return formateMilliSeccond(durationStr!!.toLong())
    }

    /**
     * Function to convert milliseconds time to
     * Timer Format
     * Hours:Minutes:Seconds
     */
    fun formateMilliSeccond(milliseconds: Long): String? {
        var finalTimerString = ""
        var secondsString = ""

        // Convert total duration into time
        val hours = (milliseconds / (1000 * 60 * 60)).toInt()
        val minutes = (milliseconds % (1000 * 60 * 60)).toInt() / (1000 * 60)
        val seconds = (milliseconds % (1000 * 60 * 60) % (1000 * 60) / 1000).toInt()

        // Add hours if there
        if (hours > 0) {
            finalTimerString = "$hours:"
        }

        // Prepending 0 to seconds if it is one digit
        secondsString = if (seconds < 10) {
            "0$seconds"
        } else {
            "" + seconds
        }
        finalTimerString = "$finalTimerString$minutes:$secondsString"

        //      return  String.format("%02d Min, %02d Sec",
        //                TimeUnit.MILLISECONDS.toMinutes(milliseconds),
        //                TimeUnit.MILLISECONDS.toSeconds(milliseconds) -
        //                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliseconds)));

        // return timer string
        return finalTimerString
    }

    fun listToString(list: List<String>, separator: String): String {
        return list.joinToString(separator)
    }

    fun parseTimeUTCtoDefault(time: String, pattern: String): Date {
        var sdf = SimpleDateFormat(
            pattern,
            Locale.getDefault()
        )
        try {
            sdf.timeZone = TimeZone.getTimeZone("UTC")
            val d = sdf.parse(time)
            sdf = SimpleDateFormat(pattern, Locale.getDefault())
            sdf.timeZone = Calendar.getInstance().timeZone
            return sdf.parse(sdf.format(d))
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return Date()
    }

    fun parseTimeUTCtoDefault(
        time: String, fromPattern: String,
        toPattern: String
    ): String {

        var sdf = SimpleDateFormat(
            fromPattern,
            Locale.getDefault()
        )
        try {
            sdf.timeZone = TimeZone.getTimeZone("UTC")
            val d = sdf.parse(time)
            sdf = SimpleDateFormat(toPattern, Locale.getDefault())
            sdf.timeZone = Calendar.getInstance().timeZone
            return sdf.format(d)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }


    fun covertTimeToText(dataDate: String?): String? {
        var convTime: String? = null
        val prefix = ""
        val suffix = "ago"
        try {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            dateFormat.timeZone = TimeZone.getTimeZone("UTC")
            val pasTime = dateFormat.parse(dataDate)
            dateFormat.timeZone = TimeZone.getDefault()
            val nowTime = Date()
            val dateDiff = nowTime.time - pasTime.time
            val second: Long = TimeUnit.MILLISECONDS.toSeconds(dateDiff)
            val minute: Long = TimeUnit.MILLISECONDS.toMinutes(dateDiff)
            val hour: Long = TimeUnit.MILLISECONDS.toHours(dateDiff)
            val day: Long = TimeUnit.MILLISECONDS.toDays(dateDiff)
            when {
                second < 0 -> {
                    convTime = "0 sec $suffix"
                }
                second < 60 -> {
                    convTime = "$second sec $suffix"
                }
                minute < 60 -> {
                    convTime = "$minute min $suffix"
                }
                hour < 24 -> {
                    convTime = "$hour hr $suffix"
                }
                day >= 7 -> {
                    convTime = when {
                        day > 360 -> {
                            (day / 360).toString() + " yr " + suffix
                        }
                        day > 30 -> {
                            (day / 30).toString() + " mo " + suffix
                        }
                        else -> {
                            (day / 7).toString() + " wk " + suffix
                        }
                    }
                }
                day < 7 -> {
                    convTime = "$day d $suffix"
                }
            }
        } catch (e: ParseException) {
            e.printStackTrace()
            Log.e("ConvTimeE", e.message!!)
        }
        return convTime
    }

    fun covertTimeToAgo(past: Date): String? {
        var convTime: String? = null
        val suffix = "ago"

        try {
            val now = Date()
            val seconds = TimeUnit.MILLISECONDS.toSeconds(now.time - past.time)
            val minutes = TimeUnit.MILLISECONDS.toMinutes(now.time - past.time)
            val hours = TimeUnit.MILLISECONDS.toHours(now.time - past.time)
            val days = TimeUnit.MILLISECONDS.toDays(now.time - past.time)

            convTime = if (seconds < 60) {
                ("$seconds s $suffix")
            } else if (minutes < 60) {
                ("$minutes min $suffix")
            } else if (hours < 24) {
                ("$hours h $suffix")
            } else if (days >= 7) {
                when {
                    days > 360 -> {
                        (days / 360).toString() + " yr " + suffix
                    }
                    days > 30 -> {
                        (days / 30).toString() + " mo " + suffix
                    }
                    else -> {
                        (days / 7).toString() + " wk " + suffix
                    }
                }
            } else if (days < 7) {
                "$days d $suffix"
            } else {
                ""
            }
        } catch (j: java.lang.Exception) {
            j.printStackTrace()
        }
        return convTime
    }

    fun getFormattedDate(dataDate: String?): String? {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        val pasTime = dateFormat.parse(dataDate)
        dateFormat.timeZone = TimeZone.getDefault()
        val notificationTime = Calendar.getInstance()
        notificationTime.timeInMillis = pasTime.time
        val now = Calendar.getInstance()
        val timeFormatString = "h:mm aa"
        val dateTimeFormatString = "EEEE, MMMM d, h:mm aa"
        return if (now[Calendar.DATE] === notificationTime[Calendar.DATE]) {
            "Today" /*+ DateFormat.format(timeFormatString, smsTime)*/
        } else if (now[Calendar.DATE] - notificationTime[Calendar.DATE] === 1) {
            "Yesterday" /*+ DateFormat.format(timeFormatString, smsTime)*/
        }
        /*else if (now[Calendar.YEAR] === notificationTime[Calendar.YEAR]) {
            DateFormat.format(dateTimeFormatString, notificationTime).toString()
        }*/
        else {
            DateFormat.format("dd MMM yyyy", notificationTime).toString()
        }
    }

    fun getAgeFromBirthDate(year: Int, month: Int, day: Int): String? {
        val dob = Calendar.getInstance()
        val today = Calendar.getInstance()
        dob[year, month] = day
        var age = today[Calendar.YEAR] - dob[Calendar.YEAR]
        if (today[Calendar.DAY_OF_YEAR] < dob[Calendar.DAY_OF_YEAR]) {
            age--
        }
        val ageInt = age
        return ageInt.toString()
    }

}