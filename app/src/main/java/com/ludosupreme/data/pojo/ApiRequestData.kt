package com.ludosupreme.data.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.File

class ApiRequestData {

    @SerializedName("signup_type")
    @Expose
    var signup_type: String? = null

    @SerializedName("social_id")
    @Expose
    var social_id: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("country_code")
    @Expose
    var country_code: String? = null

    @SerializedName("phone")
    @Expose
    var phone: String? = null

    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("password")
    @Expose
    var password: String? = null

    @SerializedName("device_type")
    @Expose
    var device_type: String? = null

    @SerializedName("device_token")
    @Expose
    var device_token: String? = null

    @SerializedName("uuid")
    @Expose
    var uuid: String? = null

    @SerializedName("ip")
    @Expose
    var ip: String? = null

    @SerializedName("os_version")
    @Expose
    var os_version: String? = null

    @SerializedName("model_name")
    @Expose
    var model_name: String? = null

    @SerializedName("app_version")
    @Expose
    var app_version: String? = null

    @SerializedName("device_name")
    @Expose
    var device_name: String? = null

    @SerializedName("build_version_number")
    @Expose
    var build_version_number: String? = null

    @SerializedName("city")
    @Expose
    var city: String? = null

    @SerializedName("country")
    @Expose
    var country: String? = null

    @SerializedName("age")
    @Expose
    var age: String? = null

    @SerializedName("gender")
    @Expose
    var gender: String? = null

    @SerializedName("otp")
    @Expose
    var otp: String? = null

    @SerializedName("old_password")
    @Expose
    var old_password: String? = null

    @SerializedName("new_password")
    @Expose
    var new_password: String? = null

    @SerializedName("profile_image")
    @Expose
    var profile_image: String? = null

    @SerializedName("is_social_login")
    @Expose
    var isSocialLogin: Boolean? = false

    @SerializedName("description")
    @Expose
    var description: String? = null

    @SerializedName("question")
    @Expose
    var question: String? = null

    @SerializedName("answer")
    @Expose
    var answer: String? = null

    @SerializedName("first_name")
    @Expose
    var first_name: String? = null

    @SerializedName("last_name")
    @Expose
    var last_name: String? = null

    @SerializedName("relationship")
    @Expose
    var relationship: String? = null

    @SerializedName("token")
    @Expose
    var token: String? = null

    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("is_selected")
    @Expose
    var is_selected: Boolean? = false

    @SerializedName("reminder_type")
    @Expose
    var reminder_type: String? = null

    @SerializedName("reminder_time")
    @Expose
    var reminder_time: String? = null

    @SerializedName("clock")
    @Expose
    var clock: String? = null

    @SerializedName("day")
    @Expose
    var day: String? = null

    @SerializedName("date")
    @Expose
    var date: String? = null

    @SerializedName("category_id")
    @Expose
    var category_id: String? = null

    @SerializedName("duration")
    @Expose
    var duration: String? = null

    @SerializedName("audio_wave")
    @Expose
    var audio_wave: String? = null

    @SerializedName("share_user_ids")
    @Expose
    var share_user_ids: String? = null

    @SerializedName("new_share_user_ids")
    @Expose
    var new_share_user_ids: String? = null

    @SerializedName("audio")
    @Expose
    var audio: String? = null

    @SerializedName("record_type")
    @Expose
    var record_type: String? = null

    @SerializedName("question_type_id")
    @Expose
    var question_type_id: String? = null

    @SerializedName("question_id")
    @Expose
    var question_id: String? = null

    @SerializedName("audio_file")
    @Expose
    var audio_file: File? = null

    @SerializedName("record_id")
    @Expose
    var record_id: String? = null

    @SerializedName("is_deleted")
    @Expose
    var is_deleted: Boolean? = false

    var isEmailEdit: Boolean? = false

    var isPhoneEdit: Boolean? = false


}