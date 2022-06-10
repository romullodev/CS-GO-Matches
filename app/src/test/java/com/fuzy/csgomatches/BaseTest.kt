package com.fuzy.csgomatches

import com.fuzy.csgomatches.domain.repository.Repository
import com.fuzy.csgomatches.external.ApiService
import com.fuzy.csgomatches.external.AppEndPoints
import com.fuzy.csgomatches.external.RemoteDataSourceImpl
import com.fuzy.csgomatches.infra.datasource.RemoteDataSource
import com.fuzy.csgomatches.infra.repositories.RepositoryImpl
import kotlinx.coroutines.Dispatchers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class BaseTest {

    private val remoteDataSource: RemoteDataSource by lazy {
        OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain ->
                chain.request().newBuilder()
                    .header("Authorization", "Bearer f0xYpAU8ySoXlMS7tUqD9JhVvEyPlQBF0fdwdS1qq_gOr_0mc-M")
                    .build().let {
                        chain.proceed(it)
                    }
            }).let {
            Retrofit.Builder()
                .baseUrl(AppEndPoints.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(it.build())
                .build()
        }.run {
            RemoteDataSourceImpl(
                create(ApiService::class.java),
                Dispatchers.Default
            )
        }
    }


    protected val repository: Repository by lazy {
        RepositoryImpl(remoteDataSource)
    }
}