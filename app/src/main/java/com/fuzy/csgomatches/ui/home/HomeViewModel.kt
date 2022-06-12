package com.fuzy.csgomatches.ui.home

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.fuzy.csgomatches.domain.entities.Match
import com.fuzy.csgomatches.domain.usecases.contracts.GetPagedMatches
import com.fuzy.csgomatches.util.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPagedMatchesUseCase: GetPagedMatches,
): BaseViewModel() {

    fun fetchMatches(): LiveData<PagingData<Match>> = getPagedMatchesUseCase()
}