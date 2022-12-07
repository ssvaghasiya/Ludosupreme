package com.ludosupreme.data.datasource

import com.ludosupreme.core.Session
import com.ludosupreme.data.pojo.ApiRequestData
import com.ludosupreme.data.pojo.CredentialData
import com.ludosupreme.data.pojo.DataWrapper
import com.ludosupreme.data.pojo.User
import com.ludosupreme.data.repository.UserRepository
import com.ludosupreme.data.service.AuthenticationService
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserLiveDataSource @Inject constructor(private val authenticationService: AuthenticationService) :
    BaseDataSource(), UserRepository {

    @Inject
    lateinit var session: Session

    override fun signUp(data: ApiRequestData): Single<DataWrapper<User>> {
        return execute(authenticationService.signUp(data))
            .doOnSuccess { t ->
                t.responseBody?.data?.let { user ->
                    user.token?.let { it1 -> session.userSession = it1 }
                    user.id.let { it1 -> session.userId = it1 }
                    if (t.responseBody.responseCode == 1) {
                        session.user = user
                    }
                }
            }
    }

    override fun signin(data: ApiRequestData): Single<DataWrapper<User>> {
        return execute(authenticationService.signin(data))
            .doOnSuccess { t ->
                t.responseBody?.data?.let { user ->
                    user.token?.let { it1 -> session.userSession = it1 }
                    user.id.let { it1 -> session.userId = it1 }
                    if (t.responseBody.responseCode == 1) {
                        session.user = user
                    }
                }
            }
    }

    override fun logout(): Single<DataWrapper<User>> {
        return execute(authenticationService.logout())
    }

    override fun sendOtp(data: ApiRequestData): Single<DataWrapper<User>> {
        return execute(authenticationService.sendOtp(data))
    }

    override fun forgotPassword(data: ApiRequestData): Single<DataWrapper<User>> {
        return execute(authenticationService.forgotPassword(data))
    }

    override fun changePassword(data: ApiRequestData): Single<DataWrapper<User>> {
        return execute(authenticationService.changePassword(data))
    }

    override fun updateProfile(data: ApiRequestData): Single<DataWrapper<User>> {
        return execute(authenticationService.updateProfile(data))
    }

    override fun updateDeviceInfo(data: ApiRequestData): Single<DataWrapper<User>> {
        return execute(authenticationService.updateDeviceInfo(data))
    }

    override fun getCredentials(): Single<DataWrapper<CredentialData>> {
        return execute(authenticationService.getCredentials())
    }

    override fun sendUpdateUserOtp(data: ApiRequestData): Single<DataWrapper<User>> {
        return execute(authenticationService.sendUpdateUserOtp(data))
    }

    override fun contactUs(data: ApiRequestData): Single<DataWrapper<User>> {
        return execute(authenticationService.contactUs(data))
    }

    override fun addFeedback(data: ApiRequestData): Single<DataWrapper<User>> {
        return execute(authenticationService.addFeedback(data))
    }

    override fun addRecipient(data: ApiRequestData): Single<DataWrapper<User>> {
        return execute(authenticationService.addRecipient(data))
    }

    override fun acceptRequest(data: ApiRequestData): Single<DataWrapper<User>> {
        return execute(authenticationService.acceptRequest(data))
    }

    override fun rejectRequest(data: ApiRequestData): Single<DataWrapper<User>> {
        return execute(authenticationService.rejectRequest(data))
    }

    override fun deleteRequest(data: ApiRequestData): Single<DataWrapper<User>> {
        return execute(authenticationService.deleteRequest(data))
    }


}
