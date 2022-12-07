package com.ludosupreme.exception

import com.ludosupreme.utils.Debug


class NoInternetException : NetworkException() {

    override var errMessage: String = "No Internet Available"

    override var title: String = "Alert"

    override fun printStackTrace() {
        super.printStackTrace()
        Debug.e("NoInternet", "No Internet Connection")
    }

    override fun getLocalizedMessage(): String {
        return super.getLocalizedMessage()
    }
}