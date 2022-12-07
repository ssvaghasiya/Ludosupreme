package com.ludosupreme.data.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class FbRes {

    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("first_name")
    @Expose
    var firstName: String? = null

    @SerializedName("last_name")
    @Expose
    var lastName: String? = null

    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("mobile_phone")
    @Expose
    var mobile_phone: String? = null

    @SerializedName("picture")
    @Expose
    var picture: Picture? = null

    class Data {
        @SerializedName("height")
        @Expose
        var height = 0

        @SerializedName("is_silhouette")
        @Expose
        var isSilhouette = false

        @SerializedName("url")
        @Expose
        var url: String? = null

        @SerializedName("width")
        @Expose
        var width = 0
    }

    class Picture {
        @SerializedName("data")
        @Expose
        var data: Data? = null
    }
}