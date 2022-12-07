package com.ludosupreme.ui.authentication.model
//https://github.com/russ666/all-countries-and-cities-json

import android.os.Parcelable
import androidx.fragment.app.FragmentActivity
import kotlinx.android.parcel.Parcelize
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import org.json.JSONArray
import org.json.JSONException
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import org.json.JSONObject
import kotlin.text.Charsets.UTF_8


class CountriesCities {

    companion object {
        fun listOfCountries(activity: FragmentActivity): ArrayList<String> {
            val fileName = "all_countries.json"
            val jsonString = activity.assets.open(fileName).bufferedReader().use {
                it.readText()
            }
            return Gson().fromJson(
                jsonString,
                object : TypeToken<ArrayList<String>>() {}.type
            )
        }

        fun listOfCities(activity: FragmentActivity, key: String): ArrayList<String> {
            val list = ArrayList<String>()
            try {
                val json = JSONObject(readJSON(activity))
                val array: JSONArray = json.getJSONArray(key)
                for (i in 0 until array.length()) {
                    list.add(array.getString(i))
                }
                return list
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            return list
        }

        fun readJSON(activity: FragmentActivity): String? {
            var json: String? = null
            json = try {
                // Opening data.json file
                val inputStream: InputStream = activity.assets.open("all_cities.json")
                val size: Int = inputStream.available()
                val buffer = ByteArray(size)
                // read values in the byte array
                inputStream.read(buffer)
                inputStream.close()
                // convert byte to string
                String(buffer, UTF_8)
            } catch (e: IOException) {
                e.printStackTrace()
                return json
            }
            return json
        }
    }
}