package com.fuzy.csgomatches.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fuzy.csgomatches.R
import com.fuzy.csgomatches.ui.loading.LoadingDialogViewModel

open class BaseViewModel: ViewModel(), LoadingDialogViewModel {
    //handle loading dialog to show feedback to user (this approaches does not depend on Fragments)
    private var _loadingMessage = MutableLiveData(R.string.loading_dialog_message_please_wait)
    override val loadingMessage: LiveData<Int> get() = _loadingMessage
    private var _isDialogVisible = MutableLiveData(false)
    override val isDialogVisible: LiveData<Boolean> get() = _isDialogVisible

    override fun showDialog(message: Int?) {
        _loadingMessage.value = message
        _isDialogVisible.value = true
    }

    override fun hideDialog() {
        _isDialogVisible.value = false
    }
}