package com.liaction.ymwy

import android.util.Log
import com.liaction.ymwy.myapplication.BuildConfig

fun Any?.isNull() = null == this

fun Any?.isNotNull() = !isNull()


fun ymwyLog(message: String? = "") {
    if (!BuildConfig.DEBUG) {
        return
    }
    message?.let {
        Log.e("ymwy log ", it)
    }
}