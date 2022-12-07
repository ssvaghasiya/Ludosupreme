package com.ludosupreme.di.module

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.os.Build
import com.amazonaws.ClientConfiguration
import com.amazonaws.Protocol
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobileconnectors.s3.transferutility.TransferNetworkLossHandler
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility
import com.amazonaws.regions.Region
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3Client
import com.ludosupreme.core.AppSession
import com.ludosupreme.core.Session
import com.ludosupreme.utils.S3Manager
import dagger.Module
import dagger.Provides
import java.io.File
import java.util.*
import javax.inject.Named
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Provides
    @Named("cache")
    internal fun provideCacheDir(application: Application): File {
        return application.cacheDir
    }

    @Provides
    @Singleton
    internal fun provideResources(application: Application): Resources {
        return application.resources
    }

    @Provides
    @Singleton
    internal fun provideCurrentLocale(resources: Resources): Locale {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            resources.configuration.locales.get(0)
        } else {
            resources.configuration.locale
        }
    }

    @Provides
    @Singleton
    internal fun provideApplicationContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    internal fun getSession(session: AppSession): Session = session


    @Provides
    @Singleton
    @Named("aes-key")
    internal fun provideAESKey(): String {
        return "VlF7TndxeviBjGQR0ttSxUZPY8Cbdtjx"
    }

    @Provides
    @Singleton
    internal fun providesAmazonS3(application: Application): AmazonS3 {

        val timeoutConnection = 600000

        val clientConfiguration = ClientConfiguration()
        clientConfiguration.maxErrorRetry = 5
        clientConfiguration.connectionTimeout = timeoutConnection
        clientConfiguration.socketTimeout = timeoutConnection
        clientConfiguration.protocol = Protocol.HTTP

        TransferNetworkLossHandler.getInstance(application)
        val awsCredentials = BasicAWSCredentials(S3Manager.aWSAccessKeyId, S3Manager.aWSSecretKey)
        return AmazonS3Client(
            awsCredentials,
            Region.getRegion(S3Manager.aWSRegion),
            clientConfiguration
        )
    }


    @Provides
    @Singleton
    internal fun provideTransferUtility(application: Application, s3: AmazonS3): TransferUtility {
        return TransferUtility.builder()
            .context(application)
            .awsConfiguration(AWSMobileClient.getInstance().configuration)
            .s3Client(s3)
            .build()
    }

}
