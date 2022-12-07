package com.ludosupreme.data


import okhttp3.HttpUrl

object URLFactory {

    // server details
    private const val IS_LOCAL = true
    private const val SCHEME = "http"
    private const val PORT_NUMBER = 3000
    private val HOST = if (IS_LOCAL) "15.184.232.111" else "skkyn.com"
    private val API_PATH = if (IS_LOCAL) "api/v1/" else "websitedata/api/v2/"

    fun provideHttpUrl(): HttpUrl {
        return HttpUrl.Builder()
            .scheme(SCHEME)
            .host(HOST)
            .port(PORT_NUMBER)
            .addPathSegments(API_PATH)
            .build()
    }

    object ResponseCode {
        const val SOCIAL_LOGIN_NOT_REGISTERED = 11
        const val SUCCESS = 1
        const val FAILURE = 0
    }

    object CmsPagesUrl {
        const val HELP_AND_FAQ = "http://15.184.232.111/public/faqs"
        const val TERMS_OF_SERVICE = "http://15.184.232.111/public/terms-condition"
        const val PRIVACY_POLICY = "http://15.184.232.111/public/privacy-policy"
        const val ABOUT_US = "http://15.184.232.111/public/about-us"
    }

    // API Methods
    object Method {
        const val SIGNIN = "user/signin"
        const val SIGNUP = "user/signup"
        const val LOGOUT = "user/logout"
        const val SEND_OTP = "user/send_otp"
        const val FORGOT_PASSWORD = "user/forgot_password"
        const val CHANGE_PASSWORD = "user/change_password"
        const val UPDATE_PROFILE = "user/update_profile"
        const val UPDATE_DEVICE = "user/update_device"
        const val GET_CREDENTIALS = "user/get_credentials"
        const val SEND_UPDATE_USER_OTP = "user/send_updateuser_otp"
        const val CONTACT_US = "user/contact_us"
        const val ADD_FEEDBACK = "user/add_feedback"
        const val ADD_RECIPIENT = "recipient/add_recipient"
        const val ACCEPT_REQUEST = "recipient/accept_request"
        const val REJECT_REQUEST = "recipient/reject_request"
        const val DELETE_REQUEST = "recipient/delete_request"
        const val REQUEST_LIST = "recipient/request_list"
        const val RECIPIENT_LIST = "recipient/recipient_list"
        const val JOINED_LIST = "recipient/joined_list"
        const val UPDATE_RECIPIENT = "recipient/update_recipient"
        const val SELECT_RECIPIENT = "recipient/select_recipient"
        const val REMINDER_LIST = "reminder/reminder_list"
        const val ADD_REMINDER = "reminder/add_reminder"
        const val UPDATE_REMINDER = "reminder/update_reminder"
        const val DELETE_REMINDER = "reminder/delete_reminder"
        const val GET_QUESTION = "record/get_question"
        const val CATEGORY_LIST = "record/category_list"
        const val CREATE_RECORD = "record/create_record"
        const val MY_RECORDING = "record/my_recording"
        const val DELETE_RECORDING = "record/delete_recording"
        const val GET_RECORDING = "record/get_recording"
        const val ADD_FAVORITE = "favorite/add_favorite"
        const val GET_FAVORITE = "favorite/get_favorite"
        const val GET_NOTIFICATION = "favorite/get_notification"
        const val BANNER_LIST = "user/banner_list"
        const val EDIT_RECORD = "record/edit_record"

    }


}
