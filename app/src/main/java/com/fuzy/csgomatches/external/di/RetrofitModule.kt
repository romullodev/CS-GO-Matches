package com.fuzy.csgomatches.external.di

import android.content.Context
import android.content.pm.ApplicationInfo
import com.fuzy.csgomatches.external.AppEndPoints
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        @ApplicationContext context: Context
    ): Retrofit {
        val builder = okHttpClient.newBuilder()

        // Activate logging of HTTP requests in DEBUG mode only.
        val isDebug = (context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0
        if (isDebug) {
            val logging = HttpLoggingInterceptor {
                Timber.tag("OkHttp").d(it)
            }
            logging.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(logging)
        }

        return Retrofit.Builder()
            .baseUrl(AppEndPoints.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(builder.build())
            .build()
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val okBuilder = OkHttpClient.Builder()
        okBuilder.addInterceptor(Interceptor { chain ->
                chain.request().newBuilder()
                    .header("Authorization", "Bearer f0xYpAU8ySoXlMS7tUqD9JhVvEyPlQBF0fdwdS1qq_gOr_0mc-M")
                    .build().let {
                        chain.proceed(it)
                    }
            }
        )
        okBuilder.connectTimeout(10, TimeUnit.SECONDS)
        okBuilder.readTimeout(10, TimeUnit.SECONDS)
        okBuilder.writeTimeout(10, TimeUnit.SECONDS)
        return okBuilder.build()
    }
}