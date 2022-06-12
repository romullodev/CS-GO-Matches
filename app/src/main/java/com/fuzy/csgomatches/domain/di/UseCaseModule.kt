package com.fuzy.csgomatches.domain.di

import com.fuzy.csgomatches.domain.usecases.contracts.GetMatchInDetails
import com.fuzy.csgomatches.domain.usecases.contracts.GetMatches
import com.fuzy.csgomatches.domain.usecases.contracts.GetOpponentDetails
import com.fuzy.csgomatches.domain.usecases.contracts.GetPagedMatches
import com.fuzy.csgomatches.domain.usecases.impl.GetMatchInDetailsImpl
import com.fuzy.csgomatches.domain.usecases.impl.GetMatchesImpl
import com.fuzy.csgomatches.domain.usecases.impl.GetOpponentDetailsImpl
import com.fuzy.csgomatches.domain.usecases.impl.GetPagedMatchesImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {
    @Binds
    abstract fun bindGetMatches(getMatches: GetMatchesImpl): GetMatches

    @Binds
    abstract fun bindGetMatchInDetails(getMatchInDetails: GetMatchInDetailsImpl): GetMatchInDetails

    @Binds
    abstract fun bindGetOpponentDetails(getOpponentDetails: GetOpponentDetailsImpl): GetOpponentDetails

    @Binds
    abstract fun bindGetPagedMatches(getPagedMatches: GetPagedMatchesImpl): GetPagedMatches
}