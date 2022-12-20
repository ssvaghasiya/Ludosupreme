package com.ludosupreme.ui.authentication.model


import android.os.Parcelable
import androidx.fragment.app.FragmentActivity
import com.google.gson.Gson
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class TournamentData(
    @SerializedName("entry")
    @Expose
    var entry: String? = null,

    @SerializedName("isFinished")
    @Expose
    var isFinished: Boolean = false,

    @SerializedName("player")
    @Expose
    var player: String? = null,

    @SerializedName("prizePool")
    @Expose
    var prizePool: String? = null,

    @SerializedName("rank1")
    @Expose
    var rank1: String? = null,

    @SerializedName("rank2")
    @Expose
    var rank2: String? = null,

    @SerializedName("rank3")
    @Expose
    var rank3: String? = null,

    @SerializedName("rank4")
    @Expose
    var rank4: String? = null,

    @SerializedName("time")
    @Expose
    var time: Int = 120000,

    @SerializedName("userCount")
    @Expose
    var userCount: Int = 0
) : Parcelable {
    companion object {
        fun readJsonOfTournaments(activity: FragmentActivity): List<TournamentData> {
            val fileName = "tournament_code.json"
            val jsonString = activity.assets.open(fileName).bufferedReader().use {
                it.readText()
            }
            val countryWrapper = Gson().fromJson(jsonString, CountryWrapper::class.java)
            return countryWrapper.tournaments

        }
    }

}

