package com.ludosupreme.ui.home.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PosterData {
    @SerializedName("id")
    @Expose
    var id = 0

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("description")
    @Expose
    var description: String? = null

    @SerializedName("image")
    @Expose
    var image: String? = null

    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("insertdate")
    @Expose
    var insertdate: String? = null

    @SerializedName("updatedate")
    @Expose
    var updatedate: String? = null

    @SerializedName("profile_image")
    @Expose
    var profileImage: String? = null
}