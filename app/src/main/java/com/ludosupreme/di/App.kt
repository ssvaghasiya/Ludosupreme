package com.ludosupreme.di

import android.app.Application
import android.content.Context
import com.ludosupreme.core.Session

class App : Application() {

    companion object {
        var session: Session? = null
    }

    override fun onCreate() {
        super.onCreate()
        Injector.INSTANCE.initAppComponent(this, "6939CB32CA9C1")
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }
}
