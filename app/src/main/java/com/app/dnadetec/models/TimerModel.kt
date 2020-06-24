package com.app.dnadetec.models

import com.google.gson.annotations.SerializedName

data class TimerModel(
    var formatedTime: String = ""
    ,var totalTimerInMillis: Long = 0L
    ,var currentTimerInMillis: Long = 0L
    ,var isStart: Boolean = false
    ,var isPause: Boolean = true
    ,var isFinish: Boolean = false
){
}