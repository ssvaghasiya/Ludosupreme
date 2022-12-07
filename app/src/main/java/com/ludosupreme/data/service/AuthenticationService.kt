package com.ludosupreme.data.service

import com.ludosupreme.data.URLFactory
import com.ludosupreme.data.pojo.ApiRequestData
import com.ludosupreme.data.pojo.CredentialData
import com.ludosupreme.data.pojo.ResponseBody
import com.ludosupreme.data.pojo.User
import com.ludosupreme.ui.home.model.NotificationData
import com.ludosupreme.ui.home.model.PosterData
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthenticationService {

    @FormUrlEncoded
    @POST(URLFactory.Method.SIGNIN)
    fun login(
        @Field("email") email: String,
        @Field("signup_type") signup_type: String,
        @Field("password") password: String,
        @Field("device_type") device_type: String,
        @Field("device_token") device_token: String,
        @Field("uuid") uuid: String,
        @Field("os_version") os_version: String,
        @Field("model_name") model_name: String,
        @Field("app_version") app_version: String,
        @Field("device_name") device_name: String,
        @Field("build_version_number") build_version_number: String
    ): Single<ResponseBody<User>>


    @POST(URLFactory.Method.SIGNUP)
    fun signUp(@Body data: ApiRequestData): Single<ResponseBody<User>>

    @POST(URLFactory.Method.SIGNIN)
    fun signin(@Body data: ApiRequestData): Single<ResponseBody<User>>

    @POST(URLFactory.Method.LOGOUT)
    fun logout(): Single<ResponseBody<User>>

    @POST(URLFactory.Method.SEND_OTP)
    fun sendOtp(@Body data: ApiRequestData): Single<ResponseBody<User>>

    @POST(URLFactory.Method.FORGOT_PASSWORD)
    fun forgotPassword(@Body data: ApiRequestData): Single<ResponseBody<User>>

    @POST(URLFactory.Method.CHANGE_PASSWORD)
    fun changePassword(@Body data: ApiRequestData): Single<ResponseBody<User>>

    @POST(URLFactory.Method.UPDATE_PROFILE)
    fun updateProfile(@Body data: ApiRequestData): Single<ResponseBody<User>>

    @POST(URLFactory.Method.UPDATE_DEVICE)
    fun updateDeviceInfo(@Body data: ApiRequestData): Single<ResponseBody<User>>

    @POST(URLFactory.Method.GET_CREDENTIALS)
    fun getCredentials(): Single<ResponseBody<CredentialData>>

    @POST(URLFactory.Method.SEND_UPDATE_USER_OTP)
    fun sendUpdateUserOtp(@Body data: ApiRequestData): Single<ResponseBody<User>>

    @POST(URLFactory.Method.CONTACT_US)
    fun contactUs(@Body data: ApiRequestData): Single<ResponseBody<User>>

    @POST(URLFactory.Method.ADD_FEEDBACK)
    fun addFeedback(@Body data: ApiRequestData): Single<ResponseBody<User>>

    @POST(URLFactory.Method.ADD_RECIPIENT)
    fun addRecipient(@Body data: ApiRequestData): Single<ResponseBody<User>>

    @POST(URLFactory.Method.ACCEPT_REQUEST)
    fun acceptRequest(@Body data: ApiRequestData): Single<ResponseBody<User>>

    @POST(URLFactory.Method.REJECT_REQUEST)
    fun rejectRequest(@Body data: ApiRequestData): Single<ResponseBody<User>>

    @POST(URLFactory.Method.DELETE_REQUEST)
    fun deleteRequest(@Body data: ApiRequestData): Single<ResponseBody<User>>



}