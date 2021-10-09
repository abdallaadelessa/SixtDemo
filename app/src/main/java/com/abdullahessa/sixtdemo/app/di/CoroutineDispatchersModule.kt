package com.abdullahessa.sixtdemo.app.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

/**
 * @author Created by Abdullah Essa on 08.10.21.
 * https://developer.android.com/kotlin/coroutines/coroutines-best-practices#inject-dispatchers
 */
@Module
@InstallIn(SingletonComponent::class)
class CoroutineDispatchersModule {
    @MainDispatcher
    @Provides
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @DefaultDispatcher
    @Provides
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @IoDispatcher
    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @UnconfinedDispatcher
    @Provides
    fun providesUnconfinedDispatcher(): CoroutineDispatcher = Dispatchers.Unconfined
}

/**
 * Annotation for injecting the default dispatcher
 */
@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MainDispatcher

/**
 * Annotation for injecting the default dispatcher
 */
@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class DefaultDispatcher

/**
 * Annotation for injecting the IO dispatcher
 */
@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IoDispatcher

/**
 * Annotation for injecting the Unconfined dispatcher
 */
@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class UnconfinedDispatcher
