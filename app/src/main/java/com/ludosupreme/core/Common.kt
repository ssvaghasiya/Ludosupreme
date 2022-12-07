package com.ludosupreme.core

import android.Manifest
import com.ludosupreme.BuildConfig


object Common {

    const val OPPS_GUEST_ACCESS_TOKEN = "opps-access-token"

    const val IS_LOGIN = "isLogin"
    const val REG_TYPE = "reg_type"
    const val APP_DIRECTORY = "Opps"

    object DateFormat {
        const val FORMAT_NODE = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        const val FORMAT_HHMMSS = "HH:mm:ss"
        const val YYYYMMDD_HHMMSS = "yyyyMMdd_HHmmss"
    }

    // notification channel constants
    const val CHANNEL_ID: String = BuildConfig.APPLICATION_ID.plus(".notifications")
    const val CHANNEL_NAME: String = "OPPS notifications"
    const val CHANNEL_DESC: String = "OPPS notifications description"

    val REQUEST_PERMISSION = 1
    val REQUEST_CAMERA_PERMISSION = 1
    val REQUEST_GALLERY_PERMISSION = 2
    val REQUEST_LOCATION_PERMISSION = 7
    val FILTER_PAGE_RESULT_CODE = 25

    const val REQUEST_CAMERA_VIDEO_PERMISSION = 3
    const val REQUEST_GALLERY_VIDEO_PERMISSION = 4
    const val REQUEST_POST_STORY_PERMISSIONS = 5

    public val PERMISSIONS_STORAGE = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE/*, Manifest.permission.WRITE_EXTERNAL_STORAGE*/)
    public val PERMISSIONS_CAMERA = arrayOf(Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE/*,
        Manifest.permission.WRITE_EXTERNAL_STORAGE*/)
    public val PERMISSIONS_RECORD_VIDEO = arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO/*,
            Manifest.permission.WRITE_EXTERNAL_STORAGE*/)
    public val PERMISSIONS_RECORD_AUDIO = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO)

    val PERMISSIONS_CALENDAR = arrayOf(Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR)

    object UserRole {
        const val EMPLOYEE = "E"
        const val COMPANY = "C"
    }

    object LoginType {
        const val SIMPLE = "S"
        const val FACEBOOK = "F"
        const val GOOGLE = "G"
    }

    object BundleKey {
        const val SOCIAL_ID = "socialId"
        const val EMAIL = "email"
        const val LOGIN_TYPE = "loginType"

        const val API_REQUEST = "apiRequest"
        const val SKILL_LIST = "skillList"
        const val USER_DATA = "userData"
        const val COMPANY_DATA = "CompanyData"
        const val EXPERIENCE_DATA = "ExpData"
        const val EDUCATION_DATA = "EduData"
        const val ID = "id"
        const val JOB_ID = "jobId"
        const val USER_ID = "userId"
        const val URL = "url"
        const val IS_FOR_RESULT = "isForResult"
        const val RECEIVER_ID = "receiverId"
        const val RECEIVER_NAME = "receiverName"
        const val RECEIVER_IMAGE = "receiverImage"
        const val CV_NAME = "cvName"
        const val CV = "cv"
        const val NOTIFICATION = "notification"
        const val IS_NOTIFICATION = "isNotification"
    }

    object RequestCode {
        const val REQUEST_TAKE_PHOTO = 1
        const val RESULT_LOAD_IMAGE = 2
        const val REQUEST_IMAGE_AND_VIDEO = 3
        const val REQUEST_FROM_CAMERA = 4
        const val REQUEST_TO_FINISH = 5
        const val REQUEST = 10
        const val REQUEST_TRIM_VIDEO = 11
        const val CROP_IMAGE_ACTIVITY_REQUEST_CODE = 203
        const val VIEW_ADVERTISEMENT = 213
        const val REQUEST_PICK_DOC = 12
        const val REQUEST_STORAGE_PERMISSION = 13
        const val REQUEST_SELECT_COMPANY = 14
        const val REQUEST_SELECT_SKILL = 15
        const val SELECT_UPLOAD_DOC = 16
        const val REQUEST_AUDIO_PERMISSION = 17
        const val PLACE_AUTOCOMPLETE_REQUEST_CODE = 18
    }

    object ResponseCode {
        val SUCCESS = 1
        val INACTIVE_ACCOUNT = 3
        val VERIFY_OTP = 4
        val SOCIAL_ID_NOT_REGISTER = 11
    }

    object NotificationTag {
        const val admin_notify = "admin_notify"
        const val push_apply_job = "push_apply_job"
        const val push_create_job = "push_create_job"
        const val push_create_post = "push_create_post"
        const val new_chat_message = "new_chat_message"
    }

    object SubscriptionType {
        const val MEMBERSHIP_1_MONTH = "com.getalong.monthly"
        const val MEMBERSHIP_2_YEAR = "com.getalong.yearly"
    }

    /**
     * chat common
     */
    const val FIREBASE_AUTH_PASSWORD = "123456"

    object RecentChat {
        val RECENT_CHAT_COLLECTION_NM = "recent_chat"
        val ANONYMOUS_ID = "anonymous_id"
        val CHAT_COUNT = "chat_count"
        val CHAT_TIME = "chat_time"
        val MESSAGE = "message"
        val MESSAGE_TYPE = "message_type"
        val RECEIVER_ID = "receiver_id"
        val RECEIVER_IMAGE = "receiver_image"
        val RECEIVER_NAME = "receiver_name"
        val SENDER_ID = "sender_id"
        val SENDER_IMAGE = "sender_image"
        val SENDER_NAME = "sender_name"
        val DESIG_TITLE = "desig_title"
        val SERVER_TIME = "server_time"

        /*
          anonymous_id
          chat_count
          chat_time
          desig_title
          message
          message_type
          receiver_id
          receiver_image
          receiver_name
          sender_id
          sender_image
          sender_name
          server_time
         */
    }

    object Chat {
        val CHAT_COLLECTION_NM = "chat"

        val CHAT_READ = "chat_read"
        val CHAT_TIME = "chat_time"
        val MESSAGE = "message"
        val MESSAGE_TYPE = "message_type"
        val RECEIVER_ID = "receiver_id"
        val RECENT_CHAT_ID = "recent_chat_id"
        val SENDER_ID = "sender_id"
        val SERVER_TIME = "server_time"
        val CHAT_MEDIA = "chat_media"
        val DESIG_TITLE = "desig_title"
        val MEDIA_THUMB = "media_thumb"
        /*
            chat_media
            chat_read
            chat_time
            desig_title
            media_thumb
            message
            message_type
            receiver_id
            recent_chat_id
            sender_id
            server_time
         */
    }

    object MsgType {
        var TEXT = "T"
        var IMAGE = "I"
        var VIDEO = "V"
        var DOC = "D"
        var AUDIO = "A"
    }

    object DefaultData {
        var DEFAULT_IMAGE = ""
        var DEFAULT_IMAGE_URL =
                "https://s3-eu-west-1.amazonaws.com/bucket-earning-pig/user/default-image.png"
        var DEFAULT_NAME = "Anonymous"
    }


    object User {
        val USER_COLLECTION_NM = "user"

        val DEVICE_TOKEN = "device_token"
        val DEVICE_TYPE = "device_type"
        val EMAIL = "email"
        val IS_ONLINE = "is_online"
        val IS_MSG_NOTIFICATION_ON = "is_message_notification_on"
        val USER_IMAGE = "user_image"
        val USER_NAME = "user_name"

        /*
            device_token
            device_type
            email
            is_message_notification_on
            is_online
            user_image
            user_name
         */
        /**
         * *********************
         */
    }
}