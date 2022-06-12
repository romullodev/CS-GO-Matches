package com.fuzy.csgomatches.domain.errors

import com.fuzy.csgomatches.R

abstract class GenericError: Exception(){
    open val resourceMsg: Int = R.string.an_error_occurred_try_again
}