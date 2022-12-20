package com.ludosupreme.ui.authentication.model


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.util.ArrayList

class CountryWrapper {
    @SerializedName("tournaments")
    @Expose
    var tournaments: List<TournamentData> = ArrayList()
}
