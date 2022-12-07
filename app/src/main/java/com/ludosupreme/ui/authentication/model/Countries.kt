package com.ludosupreme.ui.authentication.model


import android.os.Parcelable
import androidx.fragment.app.FragmentActivity
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.ludosupreme.utils.Debug
import kotlinx.android.parcel.Parcelize
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset

@Parcelize
data class Countries(
    @SerializedName("id")
    @Expose
    var id: Int? = 0,

    @SerializedName("name")
    @Expose
    var name: String? = null,

    @SerializedName("iso3")
    @Expose
    var iso3: String? = null,

    @SerializedName("iso2")
    @Expose
    var iso2: String? = null,

    @SerializedName("numeric_code")
    @Expose
    var numeric_code: String? = null,

    @SerializedName("phone_code")
    @Expose
    var phone_code: String? = null,

    @SerializedName("country_id")
    @Expose
    var country_id: Int? = 0,


    ) : Parcelable {


    companion object {
       /* fun readJsonOfCountries(activity: FragmentActivity): ArrayList<Countries> {
            val fileName = "countries.json"
            val jsonString = activity.assets.open(fileName).bufferedReader().use {
                it.readText()
            }
            return Gson().fromJson(
                jsonString,
                object : TypeToken<ArrayList<Countries>>() {}.type
            )
        }*/

        /* fun readJsonOfCities(activity: FragmentActivity): ArrayList<Countries> {
             val fileName = "cities.json"
             val jsonString = activity.assets.open(fileName).bufferedReader().use {
                 it.readText()
             }
             return Gson().fromJson(
                 jsonString,
                 object : TypeToken<ArrayList<Countries>>() {}.type
             )
         }*/

        /*fun readJsonOfCities(
            activity: FragmentActivity,
            callBack: (city: Countries) -> Unit
        ): ArrayList<Countries> {
            val list = ArrayList<Countries>()
            try {
                val inputStream: InputStream = activity.assets.open("cities.json")
                val reader: JsonReader =
                    JsonReader(InputStreamReader(inputStream, Charset.forName("UTF-8")))

                reader.beginArray()

                val gson = GsonBuilder().create()
                while (reader.hasNext()) {
                    // Read data into object model
                    val city: Countries =
                        Gson().fromJson(
                            reader,
                            Countries::class.java
                        )
                    list.add(city)
                    callBack(city)
                }
                reader.endArray()
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return list
        }*/

        /*fun listCities(activity: FragmentActivity): ArrayList<Countries> {
            val list = ArrayList<Countries>()
            try {
                val array = JSONArray(readJSON(activity))
//                val array: JSONArray = `object`.getJSONArray("data")
                for (i in 0 until array.length()) {
                    val jsonObject = array.getJSONObject(i)
                    val id = jsonObject.getInt("id")
                    val name = jsonObject.getString("name")
                    val country_id = jsonObject.getInt("country_id")
                    val model = Countries()
                    model.id = (id)
                    model.name = name
                    model.country_id = (country_id)
                    list.add(model)
                }
                val json = readJSON(activity)
                return Gson().fromJson<ArrayList<Countries>>(
                    json,
                    object : TypeToken<ArrayList<Countries>>() {}.type
                )
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            return ArrayList()
        }

        fun readJSON(activity: FragmentActivity): String? {
            var json: String? = null
            json = try {
                // Opening data.json file
                val inputStream: InputStream = activity.assets.open("cities.json")
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
        }*/
    }

    override fun toString(): String {
        return name!!
    }

}

