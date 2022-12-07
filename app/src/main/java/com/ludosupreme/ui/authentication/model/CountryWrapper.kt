package com.ludosupreme.ui.authentication.model


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ludosupreme.ui.authentication.model.Country

import java.util.ArrayList

class CountryWrapper {
    /**
     * @return The countries
     */
    /**
     * @param countries The countries
     */
    @SerializedName("countries")
    @Expose
    var countries: List<Country> = ArrayList()
    @SerializedName("nationality")
    @Expose
    val nationalitys: List<String> = ArrayList()


}
