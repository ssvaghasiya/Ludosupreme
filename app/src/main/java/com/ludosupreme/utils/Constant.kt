package com.ludosupreme.utils

object Constant {

    const val MY_PERMISSIONS_REQUEST_OPEN_GALLERY = 103
    const val MY_PERMISSIONS_REQUEST_OPEN_CAMERA = 104
    const val CROP_IMAGE_ACTIVITY_REQUEST_CODE = 203
    const val GALLERY_REQUEST = 104
    const val CAMERA_REQUEST = 106
    const val VIDEO_REQUEST = 107
    const val GALLERY_VIDEO_REQUEST = 108
    const val CropSquare = "CropSquare"
    const val CropRectangle = "CropRectangle"

    object PassValue {
        const val GALLERY_IMAGE = "gallery_image"
        const val CAMERA_IMAGE = "camera_image"
        const val IS_SIGN_UP = "is_sign_up"
        const val ORDER_ID = "order_id"
        const val RIDE_STATUS = "ride_status"
        const val PAYMENT_TYPE = "payment_type"
        const val AMOUNT = "amount"
        const val RECEIVER_ID = "receiver_id"
        const val RIDE_ID = "ride_id"
        const val PICK_UP_LAT_LNG = "pick_up_lat_lng"
        const val DROP_OFF_LAT_LNG = "drop_off_lat_lng"
        const val PICK_UP_ADDRESS = "pick_up_address"
        const val DROP_OFF_ADDRESS = "drop_off_address"
        const val VEHICLE_CATEGORY = "vehicle_category"
        const val LAT = "lat"
        const val LONG = "long"
        const val COUNTRY = "country"
        const val COUNTRY_CODE = "country_code"
        const val PHONE_NUMBER = "phone_number"
        const val EMAIL = "email"
        const val TEMP_DATA = "tempData"
        const val ADD_EDIT_ADDRESS = "add_edit_address"
        const val TITLE = "title"
        const val LINK = "link"
        const val IS_CONTACT_US = "is_contact_us"
        const val ADDRESS = "address"
        const val OTP = "otp"
        const val LATLNG = "latlng"
        const val CITY = "city"
        const val RESULTSBEAN = "ResultsBean"
        const val IS_EDIT_OTP = "is_edit_otp"
        const val ADD_NEW_ADDRESS = "add_new_address"
        const val SET_PAGE_NUMBER = "set_page_number"
        const val RESPONSE = "response"
        const val DIRECTORY_NAME = "directory_name"
        const val NOTIFICATION_CLICK = "notification_click"
    }

    object FireBase {
        const val KEY_NOTIFICATION = "key_notification"
        const val RIDEREQUEST = "RideRequest"
        const val RIDECANCELLED = "RideCancelled"
        const val RIDEACCEPTED = "RideAccepted"
        const val RIDESTARTED = "RideStarted"
        const val RIDEARRIVED = "RideArrived"
        const val RIDECOMPLETED = "RideCompleted"
        const val RIDEREJECTED = "RideRejected"
        const val NODRIVERFOUND = "NoDriverFound"
        const val RIDECONFIRMED = "RideConfirmed"

    }


    object VehicleType {
        const val BIKE = "Bike"
        const val CAR = "Car"
        const val CNG = "CNG"
        const val MICRO_BUS = "Micro_bus"
        const val AMBULANCE = "Ambulance"
        const val BUS_COACH = "Bus Coach"
    }

    enum class DocumentType {
        PROFILE,
        PASSPORT_PHOTO,
        OTHER_DOCUMENT,
        NATIONAL_ID_CARD,
        DRIVING_LICENCSE,
        DRIVER_ADDRESS,
        VEHICLE_REGISTRATION,
        TAX_TOKEN,
        COMPANY_TIN_NUMBER,
        COMPANY_TRADE_LICENCSE,
        COMPANY_REGISTRATION,
        ROUTE_PERMIT,
        FITNESS_CERTIFICATE,
        PLATE_NUMBER,
        VEHICLE_INSURANCE

    }


    object RequestedCode {
        const val ADDRESS_CODE = 10
        const val PAYMENT_INFORMATION = 202
        const val COUNTRY_CODE_REQUEST_CODE = 20
        const val COUNTRY_CODE_REQUEST_CODE_EMERGENCY = 30
        const val PICK_UP_REQUEST_CODE = 101
        const val PHONE_VERIFICATION = 105
        const val DROP_OFF_REQUEST_CODE = 102
        const val PAYMENT_METHOD = 103
        const val RIDE_LATER = 104
        const val PICK_FROM_GALLERY = 105
        const val PICK_FROM_GALLERY_CHAT = 107
        const val PICK_FROM_CAMERA = 106
        const val EDIT_PROFILE = 108
        const val REMOVE_RECORD = 120
        const val EDIT_RECORD = 121
    }


    object RideStatus {
        /*'Pending','Accepted','Rejected','Arrived','Started','Canceled','Completed','Confirmed','Not_accepted'*/
        val PENDING = "Pending"
        val ACCEPTED = "Accepted"
        val REJECTED = "Rejected"
        val ARRIVED = "Arrived"
        val STARTED = "Started"
        val CANCELED = "Canceled"
        val COMPLETED = "Completed"
        val CONFIRMED = "Confirmed"
        val NOT_ACCEPTED = "Not_accepted"
    }

    object Actions {
        const val START_SERVICE = "com.digitalride.driver.start_service"
        const val STOP_SERVICE = "com.digitalride.driver.stop_service"
    }

    object AreaType {
        val ADMINISTRATIVE_AREA_LEVEL_2 = "administrative_area_level_2"
        val ADMINISTRATIVE_AREA_LEVEL_1 = "administrative_area_level_1"
        val LOCALITY = "locality"
        val SUB_LOCALITY = "sublocality_level_1"
        val COUNTRY = "country"
        val POSTAL_CODE = "postal_code"
    }

    object Settings {
        const val ABOUT_US = "About Us"
        const val FAQ = "FAQ"
        const val TERM_AND_PRIVACY_POLICY = "Terms & Privacy Policy"
        const val TERMS_OF_SERVICE = "Terms of Service"
        const val PRIVACY_POLICY = "Privacy Policy"
        const val CONTACT_NEEBU = "Contact Neebu"
        const val FEEDBACK = "Feedback"
        const val HELP_AND_FAQ = "Help & FAQ"
        const val HEADER = "header"
        const val CMS_URL = "cms_url"
    }

    object Favourite {
        const val IS_FAVOURITE = "isFavourite"
    }

    object MyRecipient {
        const val IS_EDIT_RECIPIENT = "isEditRecipient"
        const val IS_RECIPIENT_LIST = "isRecipientList"
        const val EDIT_RECIPIENT_DATA = "editRecipientData"
    }

    object SignUpType {
        const val SIGNUP_TYPE_NORMAL = "Normal"
        const val SIGNUP_TYPE_FACEBOOK = "Facebook"
        const val SIGNUP_TYPE_GOOGLE = "Google"
        const val IS_INTRO = "isIntro"
    }

    object Gender {
        const val MALE = "M"
        const val FEMALE = "F"
    }

    object RequestStatus {
        const val PENDING = "Pending"
        const val ACCEPTED = "Accepted"
    }

    object ReminderTypes {
        const val DAILY = "Daily"
        const val WEEKLY = "Weekly"
        const val MONTHLY = "Monthly"
        const val FORTNIGHTLY = "Fortnightly"
    }

    object ReminderClock {
        const val MORNING = "Morning"
        const val AFTERNOON = "Afternoon"
        const val EVENING = "Evening"
        val dayList = arrayListOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
    }

    object Reminder {
        const val IS_EDIT_REMINDER = "is_edit_reminder"
        const val IS_EDIT_DATA = "is_edit_data"
    }

    object Record {
        const val AUDIO_WAVES = "audio_waves"
        const val SHARE_USER_IDS = "share_user_ids"
        const val RECORD_DATA = "record_data"
        const val RECORD_PREPARE_DATA = "record_prepare_data"
        const val REMOVE_RECORD_POSITION = "remove_record_position"
        const val EDIT_RECORD = "edit record"
        const val EDIT_RECORD_DATA = "edit record data"

    }

    object RecordType {
        const val FREESTYLE = "Freestyle"
        const val QUESTION = "Question"
    }

    object Category {
        const val CATEGORY_DATA = "category_data"
    }

    object PushTag {
    }

    object PushTagKEYS {
    }

    object FavouriteFlag {
        const val TRUE = "T"
        const val FALSE = "F"
        const val LIKE = "like"
        const val UNLIKE = "unlike"
    }

}
