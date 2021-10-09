package com.abdullahessa.sixtdemo.app.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

/**
 * @author Created by Abdullah Essa on 09.10.21.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideOkhttpClient(): OkHttpClient = OkHttpClient
        .Builder()
        .build()
}
