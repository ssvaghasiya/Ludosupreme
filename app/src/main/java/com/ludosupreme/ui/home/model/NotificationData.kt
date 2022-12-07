package com.ludosupreme.ui.home.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class NotificationData {
    @SerializedName("result")
    @Expose
    var result: List<Result>? = null


    class Result {
        @SerializedName("id")
        @Expose
        var id = 0

        @SerializedName("user_id")
        @Expose
        var userId = 0

        @SerializedName("receiver_id")
        @Expose
        var receiverId = 0

        @SerializedName("recipient_id")
        @Expose
        var recipient_id = 0

        @SerializedName("message")
        @Expose
        var message: String? = null

        @SerializedName("notification_type")
        @Expose
        var notificationType: String? = null

        @SerializedName("insertdate")
        @Expose
        var insertdate: String? = null

        @SerializedName("updatedate")
        @Expose
        var updatedate: String? = null

        @SerializedName("profile_image")
        @Expose
        var profileImage: String? = null

        @SerializedName("first_name")
        @Expose
        var first_name: String? = null

        @SerializedName("last_name")
        @Expose
        var last_name: String? = null

        var title: String? = null

        var type: Int = 1

    }
}