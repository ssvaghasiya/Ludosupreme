package com.ludosupreme.exception

open class BaseException() : Exception() {

    open lateinit var errMessage : String
    open lateinit var title : String


}