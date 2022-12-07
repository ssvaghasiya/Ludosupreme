package com.ludosupreme.core


import com.ludosupreme.data.pojo.CredentialData
import com.ludosupreme.data.pojo.User

public interface Session {

    var apiKey: String

    var userSession: String

    var userId: String

    var deviceId: String

    var user: User?

    val language: String

    var credential: CredentialData?

    fun clearSession()

    companion object {
        const val API_KEY = "API-KEY"
        const val USER_SESSION = "TOKEN"
        const val USER_ID = "USER_ID"
        const val DEVICE_TYPE = "A"
    }
}
