package com.fuzy.csgomatches.infra.di

import com.fuzy.csgomatches.domain.repository.Repository
import com.fuzy.csgomatches.infra.repositories.RepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindRepository(repository: RepositoryImpl): Repository
}