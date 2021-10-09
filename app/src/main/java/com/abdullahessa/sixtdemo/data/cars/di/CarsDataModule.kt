package com.abdullahessa.sixtdemo.data.cars.di

import com.abdullahessa.sixtdemo.data.cars.service.CarsDataService
import com.abdullahessa.sixtdemo.data.cars.service.CarsDataServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi


/**
 * @author Created by Abdullah Essa on 09.10.21.
 */
@Module
@InstallIn(SingletonComponent::class)
@OptIn(ExperimentalSerializationApi::class)
object CarsDataModule {
    @Provides
    fun provideCarsDataService(impl: CarsDataServiceImpl): CarsDataService = impl
}
