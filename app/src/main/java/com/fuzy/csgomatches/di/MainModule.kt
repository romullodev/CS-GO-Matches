package com.fuzy.csgomatches.di

import com.fuzy.csgomatches.external.di.ApiServiceModule
import com.fuzy.csgomatches.external.di.DataSourceModule
import com.fuzy.csgomatches.infra.di.RepositoryModule
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(
    includes = [
        RepositoryModule::class,
        ApiServiceModule::class,
        DataSourceModule::class,
    ]
)
@InstallIn(SingletonComponent::class)
class MainModule