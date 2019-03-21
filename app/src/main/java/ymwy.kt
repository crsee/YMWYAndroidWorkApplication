package com.liaction.ymwy

import android.content.Context
import android.util.Log
import android.widget.Toast
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

fun ymwyToast(context: Context, message:String) {
    if (message.isNull()){
        return
    }
    Toast.makeText(context,message , Toast.LENGTH_SHORT).show()
}