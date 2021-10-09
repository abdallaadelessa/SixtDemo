package com.abdullahessa.sixtdemo.domain.cars.di

import com.abdullahessa.sixtdemo.domain.cars.service.CarsService
import com.abdullahessa.sixtdemo.domain.cars.service.CarsServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author Created by Abdullah Essa on 09.10.21.
 */
@Module
@InstallIn(SingletonComponent::class)
object CarsModule {
    @Singleton
    @Provides
    fun provideCarsService(impl: CarsServiceImpl): CarsService = impl
}
