package com.ludosupreme.data.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CredentialData {

    @SerializedName("Url")
    @Expose
    var url: String? = null

    @SerializedName("Name")
    @Expose
    var name: String? = null

    @SerializedName("Region")
    @Expose
    var region: String? = null

    @SerializedName("Access_Key")
    @Expose
    var accessKey: String? = null

    @SerializedName("Secret_Key")
    @Expose
    var secretKey: String? = null
}