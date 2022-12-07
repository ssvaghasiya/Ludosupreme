package com.ludosupreme.utils

import android.annotation.SuppressLint
import android.content.Context


import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import androidx.annotation.StringDef
import java.text.ParseException
import kotlin.math.pow
import kotlin.math.roundToInt

/**
 * A class to format datetime strings
 */
object Formatter {
    const val JAN_2017 = "MMM / yyyy"
    const val MM_YY = "MM/yy"
    const val MMM = "MMM"
    const val MM = "MM"
    const val MMMM = "MMMM"
    const val YYYY = "yyyy"
    const val MMM_YYYY = "MMM yyyy"
    const val MMMM_YYYY = "MMMM yyyy"
    const val EEEE = "EEEE"

    //public static final String DD_MM_YYYY = "dd/ MM /yyyy";
    const val DD_MMM_YYYY = "dd MMM yyyy"
    const val DD_MM_YYYY_LINE = "dd/MM/yyyy"
    const val MMMM_YYYY_FULL = "MMMM yyyy"
    const val MMMM_DD_YYYY_FULL = "MMMM dd, yyyy"
    const val DD_MMMM_YYYY_FULL = "dd MMMM, yyyy"
    const val DD_MMM_YYYY_FULL = "dd MMM, yyyy"
    const val DD_MM_YYYY = "dd-MMM-yyyy"
    const val YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss"
    const val YYYY_MM_DD_hh_mm_aa = "yyyy-MM-dd hh:mm aa"
    const val DD_MMMM_YYYY_hh_mm_aa = "dd MMMM yyyy hh:mm aa"
    const val DD_MMM_YYYY_hh_mm_aa = "dd MMM, yyyy hh:mmaa"
    const val CALL_LOG_TIME = "dd MMM, yyyy - hh:mm aa"
    const val YYYY_MM_DD_T_HH_MM_SSS_Z = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    const val YYYY_MM_DD_T_HH_MM_SSS = "yyyy-MM-dd'T'HH:mm:ss.SSS"

    const val DD_MMM_YYYY_HH_MM_a = "dd MMM, yyyy hh:mm aa"

    // public static final String DD_MM_YYYY = "dd.MM.yyyy";
    const val YYYY_MM_DD = "yyyy-MM-dd"
    const val MM_DD_YYYY = "MM/dd/yyyy"
    const val HH_mm = "HH:mm"
    const val h_mm_a = "h:mm a"
    const val HH_mm_ss = "HH:mm:ss"
    const val hh_mm_aa = "hh:mm aa"
    const val h_aa = "h:mm aa"
    const val EEEE_hh_mm_aa = "EEEE hh:mm aa"
    const val EEE_dd_MMM_yyyy = "EEE, dd MMM yyyy"
    const val DD_MMM = "dd MMM"
    const val DD = "DD"
    const val DD_hh_mm = "dd hh:mma"

    // CREATE DateFormatSymbols WITH ALL SYMBOLS FROM (DEFAULT) Locale
    private var symbols: DateFormatSymbols? = null
    private const val TAG = "Formatter"
    private const val UTC = "GMT"
    private const val SECOND: Long = 1000
    private const val MINUTE = SECOND * 60
    private const val HOUR = MINUTE * 60
    private const val DAY = HOUR * 24
    private const val MONTH = DAY * 30
    private const val YEAR = MONTH * 12
    private const val WEEK = DAY * 7

    var suffixes = arrayOf(
            "th",
            "st",
            "nd",
            "rd",
            "th",
            "th",
            "th",
            "th",
            "th",
            "th",  //    10    11    12    13    14    15    16    17    18    19
            "th",
            "th",
            "th",
            "th",
            "th",
            "th",
            "th",
            "th",
            "th",
            "th",  //    20    21    22    23    24    25    26    27    28    29
            "th",
            "st",
            "nd",
            "rd",
            "th",
            "th",
            "th",
            "th",
            "th",
            "th",  //    30    31
            "th",
            "st"
    )

    private const val inputTimeZone = UTC
    fun round(value: Double, places: Int): Double {
        var value = value
        require(places >= 0)
        val factor = 10.0.pow(places.toDouble()).toLong()
        value *= factor
        val tmp = value.roundToInt()
        return tmp.toDouble() / factor
    }

    fun round(value: Double): String {
        var value = value
        val factor = 10.0.pow(2.0).toLong()
        value *= factor
        val tmp = value.roundToInt()
        val valueV = tmp.toDouble() / factor
        return String.format(Locale.US, "%.2f", valueV)
    }

    /**
     * Check if two calendar objects has same date,month,year
     *
     * @param first
     * @param second
     * @return
     */
    fun matches(first: Calendar, second: Calendar): Boolean {
        val fDay = first[Calendar.DAY_OF_MONTH]
        val fMonth = first[Calendar.MONTH]
        val fYear = first[Calendar.YEAR]
        val sDay = second[Calendar.DAY_OF_MONTH]
        val sMonth = second[Calendar.MONTH]
        val sYear = second[Calendar.YEAR]
        return fDay == sDay && fMonth == sMonth && fYear == sYear
    }

    fun format(
            locale: Locale?,
            datetime: String?,
            @FORMAT inFormat: String?,
            @FORMAT outFormat: String?,
            isUtc: Boolean
    ): String? {
        try {
            val input = SimpleDateFormat(inFormat, locale)
            if (isUtc) input.timeZone = TimeZone.getTimeZone(inputTimeZone)
            val date = input.parse(datetime)
            val output = SimpleDateFormat(outFormat, locale)
            output.timeZone = TimeZone.getDefault()
            output.dateFormatSymbols = symbols
            return output.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    @JvmOverloads
    fun format(
            datetime: String?,
            @FORMAT inFormat: String?,
            @FORMAT outFormat: String?,
            isUtc: Boolean = false
    ): String? {
        return format(Locale.US, datetime, inFormat, outFormat, isUtc)
    }

    @JvmOverloads
    fun format(datetime: Calendar, @FORMAT outFormat: String?, isUtc: Boolean = false): String? {
        try {
            val date = datetime.time
            val output = SimpleDateFormat(outFormat, Locale.US)
            if (isUtc) output.timeZone = TimeZone.getTimeZone(inputTimeZone) else output.timeZone =
                    TimeZone.getDefault()
            output.dateFormatSymbols = symbols
            return output.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun format(date: Date, @FORMAT outFormat: String?): String? {
        try {
            val output = SimpleDateFormat(outFormat, Locale.US)
            output.dateFormatSymbols = symbols
            output.timeZone = TimeZone.getDefault()
            return output.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun getCalendar(datetime: String?, @FORMAT inFormat: String?): Calendar? {
        return getCalendar(datetime, inFormat, false)
    }

    fun getCalendar(datetime: String?, @FORMAT inFormat: String?, isUtc: Boolean): Calendar? {
        try {
            val output = SimpleDateFormat(inFormat, Locale.US)
            output.dateFormatSymbols = symbols
            /*if (isUtc)*/output.timeZone = TimeZone.getTimeZone("GMT")
            val calendar = Calendar.getInstance()
            calendar.timeZone = TimeZone.getDefault()
            val date = output.parse(datetime)
            if (date != null) calendar.time = date
            return calendar
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * This method only use for check if party start and not its hours more then 2
     * please do not use this method for any other use
     *
     * @param context
     * @param locale
     * @param formattedString
     * @param datetime
     * @param inFormat
     * @param isUtc
     * @return
     */
    fun isPreviousTime(
            context: Context?, locale: Locale?, formattedString: String?,
            datetime: String?, @FORMAT inFormat: String?,
            isUtc: Boolean
    ): Boolean {
        try {
            val date = getCalendar(datetime, inFormat, isUtc)!!.time

            //Initialize both calendar with set time
            val calendarDate = Calendar.getInstance()
            calendarDate.time = date
            val current = Calendar.getInstance()

            //DebugLog.i("InsertDate::::"+ Formatter.format(calendarDate, DD_MMMM_YYYY_hh_mm_aa));
            //DebugLog.i("CurrentDate::::"+ Formatter.format(current, DD_MMMM_YYYY_hh_mm_aa));
            val difference = current.timeInMillis - calendarDate.timeInMillis
            val hour = difference / HOUR
            if (hour < 2) {
                return false
            }
        } catch (e: Exception) {
        }
        return true
    }

    fun isPartyStarted(
            context: Context?, locale: Locale?, formattedString: String?,
            datetime: String?, @FORMAT inFormat: String?,
            isUtc: Boolean
    ): Boolean {
        try {
            val date = getCalendar(datetime, inFormat, isUtc)!!.time

            //Initialize both calendar with set time
            val calendarDate = Calendar.getInstance()
            calendarDate.time = date
            val current = Calendar.getInstance()

            //DebugLog.i("InsertDate::::"+ Formatter.format(calendarDate, DD_MMMM_YYYY_hh_mm_aa));
            //DebugLog.i("CurrentDate::::"+ Formatter.format(current, DD_MMMM_YYYY_hh_mm_aa));
            val difference = current.timeInMillis - calendarDate.timeInMillis
            val hour = difference / HOUR
            if (hour < 0) {
                return false
            }
        } catch (e: Exception) {
        }
        return true
    }

    fun isPartyRunning(
            context: Context?, locale: Locale?, formattedString: String?,
            datetime: String?, @FORMAT inFormat: String?,
            isUtc: Boolean
    ): Boolean {
        try {
            val date = getCalendar(datetime, inFormat, isUtc)!!.time

            //Initialize both calendar with set time
            val calendarDate = Calendar.getInstance()
            calendarDate.time = date
            val current = Calendar.getInstance()

            //DebugLog.i("InsertDate::::"+ Formatter.format(calendarDate, DD_MMMM_YYYY_hh_mm_aa));
            //DebugLog.i("CurrentDate::::"+ Formatter.format(current, DD_MMMM_YYYY_hh_mm_aa));
            val difference = current.timeInMillis - calendarDate.timeInMillis
            val hour = difference / HOUR
            if (difference > 0 && hour >= 0 && hour < 2) {
                return true
            }
        } catch (e: Exception) {
        }
        return false
    }

    fun getPrettyTime(
            context: Context?,
            locale: Locale?,
            formattedString: String?,
            datetime: String?,
            @FORMAT inFormat: String?,
            isUtc: Boolean
    ): String {
        try {
            val date = getCalendar(datetime, inFormat, isUtc)!!.time

            //Initialize both calendar with set time
            val calendarDate = Calendar.getInstance()
            calendarDate.time = date
            val current = Calendar.getInstance()

            //DebugLog.i("InsertDate::::"+ Formatter.format(calendarDate, DD_MMMM_YYYY_hh_mm_aa));
            //DebugLog.i("CurrentDate::::"+ Formatter.format(current, DD_MMMM_YYYY_hh_mm_aa));
            val difference = current.timeInMillis - calendarDate.timeInMillis
            val year = difference / YEAR
            val month = difference / MONTH
            val day = difference / DAY
            val week = difference / WEEK
            val hour = difference / HOUR
            val minute = difference / MINUTE
            val second = difference / SECOND
            val time: String? = null
            /*if (year > 0)
                time = String.format(locale, "%d %s", year
                        , year > 1 ? context.getString(R.string.years_ago) : context.getString(R.string.year_ago));
            else if (month > 0)
                time = String.format(locale, "%d %s", month
                        , month > 1 ? context.getString(R.string.months_ago) : context.getString(R.string.month_ago));
            else if (week > 0)
                time = String.format(locale, "%d %s", week
                        , week > 1 ? context.getString(R.string.weeks_ago) : context.getString(R.string.week_ago));
            else if (day > 0)
                time = String.format(locale, "%d %s", day
                        , day > 1 ? context.getString(R.string.days_ago) : context.getString(R.string.day_ago));
            else if (hour > 0)
                time = String.format(locale, "%d %s", hour
                        , hour > 1 ? context.getString(R.string.hours_ago) : context.getString(R.string.hour_ago));
            else if (minute > 0)
                time = String.format(locale, "%d %s", minute
                        , minute > 1 ? context.getString(R.string.minutes_ago) : context.getString(R.string.minute_ago));
            else if (second > 0)
                time = String.format(locale, "%d %s", second
                        , second > 1 ? context.getString(R.string.seconds_ago) : context.getString(R.string.second_ago));

            if (time != null)
                return String.format(formattedString, time);

            return context.getString(R.string.just_now);*/
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    fun getTimeorDate(datetime: String?, @FORMAT inFormat: String?, isUtc: Boolean): String? {
        try {
            //Initialize both calendar with set time
            val calendarDate = getCalendar(datetime, inFormat, isUtc)
            val current = Calendar.getInstance()
            return if (matches(calendarDate!!, current)) {
                format(calendarDate.time, hh_mm_aa)
            } else {
                format(calendarDate.time, CALL_LOG_TIME)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    fun getDateString(@FORMAT outFormat: String?): String? {
        return format(Calendar.getInstance(), outFormat)
    }

    fun convert(timestamp: String): String? {
        var localTime: String? = null
        try {
            val time = java.lang.Long.valueOf(timestamp)
            val cal = Calendar.getInstance()
            val tz = cal.timeZone

            /* debug: is it local time? */
            // DebugLog.d("Time zone: ", tz.getDisplayName());

            /* date formatter in local timezone */
            val sdf = SimpleDateFormat(YYYY_MM_DD_HH_MM_SS)
            sdf.timeZone = tz

            /* print your timestamp and double check it's the date you expect */
            // I assume your timestamp is in seconds and you're converting to milliseconds?
            localTime = sdf.format(Date(time * 1000))
            //DebugLog.d("Time: ", localTime);
            return localTime
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }
        return ""
    }

    fun localToUTC(dateFormat: String?, datesToConvert: String?): String? {

        var dateToReturn = datesToConvert

        val sdf = SimpleDateFormat(dateFormat)
        sdf.timeZone = TimeZone.getDefault()

        var gmt: Date? = null

        val sdfOutPutToSend = SimpleDateFormat(dateFormat)

        sdfOutPutToSend.timeZone = TimeZone.getTimeZone("UTC")

        try {

            gmt = sdf.parse(datesToConvert)
            dateToReturn = sdfOutPutToSend.format(gmt)

        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return dateToReturn
    }

    fun utcToLocal(dateFormatIn: String, dateFormatOut: String, datesToConvert: String): String {
        var dateToReturn = datesToConvert
        val sdf = SimpleDateFormat(dateFormatIn)
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        var gmt: Date? = null
        val sdfOutPutToSend = SimpleDateFormat(dateFormatOut)
        sdfOutPutToSend.timeZone = TimeZone.getDefault()
        try {
            gmt = sdf.parse(datesToConvert)
            dateToReturn = sdfOutPutToSend.format(gmt)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return dateToReturn
    }

    @SuppressLint("SimpleDateFormat")
    fun stringToDate(string: String): Date = SimpleDateFormat(YYYY_MM_DD_HH_MM_SS).parse(string)

    @SuppressLint("SimpleDateFormat")
    fun stringToDateYYYY_MM_DD(string: String): Date = SimpleDateFormat(YYYY_MM_DD).parse(string)

    @Retention(RetentionPolicy.SOURCE)
    @StringDef(
            MM_DD_YYYY,
            YYYY_MM_DD,
            JAN_2017,
            DD_MMM_YYYY,
            MM_YY,
            YYYY_MM_DD_HH_MM_SS,
            YYYY_MM_DD_T_HH_MM_SSS_Z,
            YYYY_MM_DD_hh_mm_aa,
            CALL_LOG_TIME,
            DD_MMMM_YYYY_hh_mm_aa,
            DD_MMM_YYYY_hh_mm_aa,
            DD_MM_YYYY,
            MM,
            MMM,
            MMMM,
            YYYY,
            DD_MM_YYYY,
            DD_MMMM_YYYY_FULL,
            DD_MMM_YYYY_FULL,
            HH_mm,
            HH_mm_ss,
            hh_mm_aa,
            EEEE_hh_mm_aa,
            EEE_dd_MMM_yyyy,
            MMM_YYYY,
            DD_MMM,
            MMMM_YYYY,
            EEEE
    )
    annotation class FORMAT

    fun getLocalToUTCDate(date: Date?): String? {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.timeZone = TimeZone.getTimeZone("UTC")
        val time = calendar.time
        @SuppressLint("SimpleDateFormat") val outputFmt =
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        outputFmt.timeZone = TimeZone.getTimeZone("UTC")
        return outputFmt.format(time)
    }

    fun getUTCToLocalDate(date: String?): Date? {
        var inputDate = Date()
        if (date != null && date.isNotEmpty()) {
            @SuppressLint("SimpleDateFormat") val simpleDateFormat =
                    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            simpleDateFormat.timeZone = TimeZone.getTimeZone("UTC")
            try {
                inputDate = simpleDateFormat.parse(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }
        return inputDate
    }

    init {
        // OVERRIDE SOME symbols WHILE RETAINING OTHERS
        symbols = DateFormatSymbols(Locale.getDefault())
        symbols?.amPmStrings = arrayOf("am", "pm")
    }
}
