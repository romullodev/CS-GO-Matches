package com.fuzy.csgomatches.ui.loading

import android.app.Dialog
import android.content.Context
import androidx.annotation.StyleRes
import com.fuzy.csgomatches.R

class CustomDialog(context: Context, @StyleRes dialogStyle: Int) : Dialog(context, dialogStyle) {
    init {
        this.window?.setBackgroundDrawableResource(R.color.a_60_black)
    }
}