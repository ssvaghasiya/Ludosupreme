package com.ludosupreme.ui.authentication.model


import android.os.Parcelable
import androidx.fragment.app.FragmentActivity
import com.google.gson.Gson
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Country(@SerializedName("code")
                   @Expose
                   var code: String? = null,


                   @SerializedName("name")
                   @Expose
                   var name: String? = null,


                   @SerializedName("name_short")
                   @Expose
                   var nameShort: String? = null) : Parcelable {


    companion object {
        fun readJsonOfCountries(activity: FragmentActivity): List<Country> {
            val fileName = "country_code.json"
            val jsonString = activity.assets.open(fileName).bufferedReader().use {
                it.readText()
            }
            val countryWrapper = Gson().fromJson(jsonString, CountryWrapper::class.java)
            return countryWrapper.countries

        }
    }

}

