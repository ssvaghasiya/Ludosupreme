package com.ludosupreme.data.repository

import com.ludosupreme.data.pojo.ApiRequestData
import com.ludosupreme.data.pojo.CredentialData
import com.ludosupreme.data.pojo.DataWrapper
import com.ludosupreme.data.pojo.User
import io.reactivex.Single

interface UserRepository {

    fun signUp(data: ApiRequestData): Single<DataWrapper<User>>

    fun signin(data: ApiRequestData): Single<DataWrapper<User>>

    fun logout(): Single<DataWrapper<User>>

    fun sendOtp(data: ApiRequestData): Single<DataWrapper<User>>

    fun forgotPassword(data: ApiRequestData): Single<DataWrapper<User>>

    fun changePassword(data: ApiRequestData): Single<DataWrapper<User>>

    fun updateProfile(data: ApiRequestData): Single<DataWrapper<User>>

    fun updateDeviceInfo(data: ApiRequestData): Single<DataWrapper<User>>

    fun getCredentials(): Single<DataWrapper<CredentialData>>

    fun sendUpdateUserOtp(data: ApiRequestData): Single<DataWrapper<User>>

    fun contactUs(data: ApiRequestData): Single<DataWrapper<User>>

    fun addFeedback(data: ApiRequestData): Single<DataWrapper<User>>

    fun addRecipient(data: ApiRequestData): Single<DataWrapper<User>>

    fun acceptRequest(data: ApiRequestData): Single<DataWrapper<User>>

    fun rejectRequest(data: ApiRequestData): Single<DataWrapper<User>>

    fun deleteRequest(data: ApiRequestData): Single<DataWrapper<User>>


}