package com.fuzy.csgomatches.external.di

import com.fuzy.csgomatches.external.RemoteDataSourceImpl
import com.fuzy.csgomatches.infra.datasource.RemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class DataSourceModule {
    @Binds
    abstract fun bindRemoteDataSourceRetrofitModule(remoteDataSource: RemoteDataSourceImpl): RemoteDataSource
}