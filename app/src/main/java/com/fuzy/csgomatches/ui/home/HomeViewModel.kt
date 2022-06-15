package com.fuzy.csgomatches.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.fuzy.csgomatches.R
import com.fuzy.csgomatches.domain.entities.Match
import com.fuzy.csgomatches.domain.errors.GenericError
import com.fuzy.csgomatches.domain.usecases.contracts.GetMatchInDetails
import com.fuzy.csgomatches.domain.usecases.contracts.GetPagedMatches
import com.fuzy.csgomatches.util.BaseViewModel
import com.fuzy.csgomatches.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPagedMatchesUseCase: GetPagedMatches,
    private val getMatchInDetailsUseCase: GetMatchInDetails,
): BaseViewModel() {

    private val _homeStates = MutableLiveData<Event<HomeStates>>()
    val homeState: LiveData<Event<HomeStates>> get() = _homeStates

    fun fetchMatches(): LiveData<PagingData<Match>> = getPagedMatchesUseCase()

    fun getMatchDetails(match: Match) {
        showDialog()
        viewModelScope.launch {
            try {
                getMatchInDetailsUseCase(match).run {
                    _homeStates.value = Event(HomeStates.GetMatchInDetailSuccessfully(
                        this
                    ))
                }
            }catch (e: Exception){
                _homeStates.value = Event(HomeStates.HomeAlertDialog(
                    msg = (e as? GenericError)?.resourceMsg
                ))
            }
            hideDialog()
        }
    }
    sealed class HomeStates {
        class HomeAlertDialog(val msg: Int?) : HomeStates()
        class GetMatchInDetailSuccessfully(val matchInDetail: Match): HomeStates()
    }
}