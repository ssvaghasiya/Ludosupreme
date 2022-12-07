package com.ludosupreme.di


import android.app.Application
import com.ludosupreme.di.component.ApplicationComponent
import com.ludosupreme.di.component.DaggerApplicationComponent

enum class Injector private constructor() {
    INSTANCE;

    lateinit var applicationComponent: ApplicationComponent
        internal set

    fun initAppComponent(application: Application, apiKey: String) {
        applicationComponent = DaggerApplicationComponent.builder()
                .application(application)
                .apiKey(apiKey)
                .build()
    }
}
