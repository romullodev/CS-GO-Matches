package com.fuzy.csgomatches.util

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

open class BaseFragment: Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        baseSetupDataBindings()
        baseSetupObservers()
        baseSetupAdapters()
        baseSetupToolbar()
        baseSetupListeners()
    }

    open fun baseSetupDataBindings(){}
    open fun baseSetupObservers(){}
    open fun baseSetupAdapters(){}
    open fun baseSetupToolbar(){}
    open fun baseSetupListeners(){}
}