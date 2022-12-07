package com.ludosupreme.data.pojo

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class User(val id: String) {

    companion object {
        const val KEY = "user"
    }

    @SerializedName("first_name")
    @Expose
    var first_name: String? = null

    @SerializedName("last_name")
    @Expose
    var last_name: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("country_code")
    @Expose
    var countryCode: String? = null

    @SerializedName("phone")
    @Expose
    var phone: String? = null

    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("country")
    @Expose
    var country: String? = null

    @SerializedName("city")
    @Expose
    var city: String? = null

    @SerializedName("age")
    @Expose
    var age = 0

    @SerializedName("gender")
    @Expose
    var gender: String? = null

    @SerializedName("profile_image")
    @Expose
    var profileImage: String? = null

    @SerializedName("signup_type")
    @Expose
    var signupType: String? = null

    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("updated_at")
    @Expose
    var updatedAt: String? = null

    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null

    @SerializedName("user_device")
    @Expose
    var userDevice: UserDevice? = null

    @SerializedName("token")
    @Expose
    var token: String? = null

    @SerializedName("otp")
    @Expose
    var otp: String? = null

    class UserDevice {
        @SerializedName("id")
        @Expose
        var id = 0

        @SerializedName("user_id")
        @Expose
        var userId = 0

        @SerializedName("token")
        @Expose
        var token: String? = null

        @SerializedName("device_token")
        @Expose
        var deviceToken: String? = null

        @SerializedName("device_type")
        @Expose
        var deviceType: String? = null

        @SerializedName("uuid")
        @Expose
        var uuid: String? = null

        @SerializedName("os_version")
        @Expose
        var osVersion: String? = null

        @SerializedName("device_name")
        @Expose
        var deviceName: String? = null

        @SerializedName("model_name")
        @Expose
        var modelName: String? = null

        @SerializedName("version_number")
        @Expose
        var versionNumber: Any? = null

        @SerializedName("build_version_number")
        @Expose
        var buildVersionNumber: String? = null

        @SerializedName("ip")
        @Expose
        var ip: Any? = null

        @SerializedName("api_version")
        @Expose
        var apiVersion: String? = null

        @SerializedName("app_version")
        @Expose
        var appVersion: String? = null

        @SerializedName("language")
        @Expose
        var language: String? = null

        @SerializedName("updated_at")
        @Expose
        var updatedAt: String? = null

        @SerializedName("created_at")
        @Expose
        var createdAt: String? = null
    }
}