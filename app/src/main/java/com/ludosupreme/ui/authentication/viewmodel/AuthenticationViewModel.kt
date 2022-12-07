package com.ludosupreme.ui.authentication.viewmodel

import com.ludosupreme.data.pojo.ApiRequestData
import com.ludosupreme.data.pojo.CredentialData
import com.ludosupreme.data.pojo.User
import com.ludosupreme.data.repository.UserRepository
import com.ludosupreme.ui.base.APILiveData
import com.ludosupreme.ui.base.BaseViewModel
import javax.inject.Inject

class AuthenticationViewModel @Inject constructor(private val userRepository: UserRepository) :
    BaseViewModel() {

    var isMale: Boolean = false
    val signUpLiveData = APILiveData<User>()
    val signinLiveData = APILiveData<User>()
    val logoutLiveData = APILiveData<User>()
    val sendOtpLiveData = APILiveData<User>()
    val forgotPasswordLiveData = APILiveData<User>()
    val changePasswordLiveData = APILiveData<User>()
    val updateProfileLiveData = APILiveData<User>()
    val updateDeviceInfoLiveData = APILiveData<User>()
    val getCredentialsLiveData = APILiveData<CredentialData>()
    val sendUpdateUserOtpLiveData = APILiveData<User>()
    val contactUsLiveData = APILiveData<User>()
    val addFeedbackLiveData = APILiveData<User>()


    fun signUp(data: ApiRequestData) {
        userRepository.signUp(data)
            .subscribe(withLiveData(signUpLiveData))
    }

    fun signin(data: ApiRequestData) {
        userRepository.signin(data)
            .subscribe(withLiveData(signinLiveData))
    }

    fun logout() {
        userRepository.logout()
            .subscribe(withLiveData(logoutLiveData))
    }

    fun sendOtp(data: ApiRequestData) {
        userRepository.sendOtp(data)
            .subscribe(withLiveData(sendOtpLiveData))
    }

    fun forgotPassword(data: ApiRequestData) {
        userRepository.forgotPassword(data)
            .subscribe(withLiveData(forgotPasswordLiveData))
    }

    fun changePassword(data: ApiRequestData) {
        userRepository.changePassword(data)
            .subscribe(withLiveData(changePasswordLiveData))
    }

    fun updateProfile(data: ApiRequestData) {
        userRepository.updateProfile(data)
            .subscribe(withLiveData(updateProfileLiveData))
    }

    fun updateDeviceInfo(data: ApiRequestData) {
        userRepository.updateDeviceInfo(data)
            .subscribe(withLiveData(updateDeviceInfoLiveData))
    }

    fun getCredentials() {
        userRepository.getCredentials()
            .subscribe(withLiveData(getCredentialsLiveData))
    }

    fun sendUpdateUserOtp(data: ApiRequestData) {
        userRepository.sendUpdateUserOtp(data)
            .subscribe(withLiveData(sendUpdateUserOtpLiveData))
    }


    fun contactUs(data: ApiRequestData) {
        userRepository.contactUs(data)
            .subscribe(withLiveData(contactUsLiveData))
    }

    fun addFeedback(data: ApiRequestData) {
        userRepository.addFeedback(data)
            .subscribe(withLiveData(addFeedbackLiveData))
    }
}