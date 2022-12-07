package com.ludosupreme.di.component

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import android.content.Context
import android.content.res.Resources
import com.ludosupreme.utils.Validator
import com.ludosupreme.core.AppPreferences
import com.ludosupreme.core.Session
import com.ludosupreme.data.repository.UserRepository
import com.ludosupreme.di.App
import com.ludosupreme.di.module.ApplicationModule
import com.ludosupreme.di.module.NetModule
import com.ludosupreme.di.module.ServiceModule
import com.ludosupreme.di.module.ViewModelModule
import com.ludosupreme.utils.S3Manager
import dagger.BindsInstance
import dagger.Component
import java.io.File
import java.util.*
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, ViewModelModule::class, NetModule::class, ServiceModule::class])
interface ApplicationComponent {

    fun context(): Context

    fun provideValidator(): Validator

    @Named("cache")
    fun provideCacheDir(): File

    fun provideResources(): Resources

    fun provideCurrentLocale(): Locale

    fun provideViewModelFactory(): ViewModelProvider.Factory

    fun inject(appShell: App)

    fun provideUserRepository(): UserRepository

    fun provideSession(): Session

    fun getAppPreferences(): AppPreferences

    fun provideS3Manager(): S3Manager

    @Component.Builder
    interface ApplicationComponentBuilder {

        @BindsInstance
        fun apiKey(@Named("api-key") apiKey: String): ApplicationComponentBuilder

        @BindsInstance
        fun application(application: Application): ApplicationComponentBuilder

        fun build(): ApplicationComponent
    }

}
