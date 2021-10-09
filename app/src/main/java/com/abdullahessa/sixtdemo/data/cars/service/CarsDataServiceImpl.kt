package com.abdullahessa.sixtdemo.data.cars.service

import com.abdullahessa.sixtdemo.data.AppConnectionError
import com.abdullahessa.sixtdemo.data.AppError
import com.abdullahessa.sixtdemo.data.AppHttpError
import com.abdullahessa.sixtdemo.data.AppResult
import com.abdullahessa.sixtdemo.data.cars.model.CarDataModel
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.http.GET
import java.io.IOException
import javax.inject.Inject

/**
 * @author Created by Abdullah Essa on 08.10.21.
 */
class CarsDataServiceImpl @Inject constructor(
    private val client: OkHttpClient
) : CarsDataService {

    private val retrofitService: CarsRetrofitService by lazy {
        Retrofit.Builder()
            .addConverterFactory(Json.asConverterFactory(MediaType.get(MEDIA_TYPE)))
            .baseUrl(BASE_URL)
            .client(client)
            .build()
            .create(CarsRetrofitService::class.java)
    }

    override suspend fun getCarList(): AppResult<List<CarDataModel>> =
        AppResult.wrap { retrofitService.getCarList() ?: emptyList() }
            .mapError { ex ->
                when (ex) {
                    is IOException -> AppConnectionError(ex)
                    is HttpException -> AppHttpError(ex.code(), ex.message(), ex)
                    else -> AppError("Unknown Error", ex)
                }
            }

    private interface CarsRetrofitService {
        @GET("cars")
        suspend fun getCarList(): List<CarDataModel>?
    }

    companion object {
        private const val BASE_URL = "https://cdn.sixt.io/codingtask/"
        private const val MEDIA_TYPE = "application/json"
    }
}
