package com.liaction.ymwy.myapplication.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.liaction.ymwy.myapplication.IYMWYAidlInterface
import com.liaction.ymwy.myapplication.bean.YMWYMessage
import com.liaction.ymwy.ymwyLog

class YMWYAidlService : Service() {

    private val mBinder = object : IYMWYAidlInterface.Stub() {
        override fun sendMessage(message: YMWYMessage?): YMWYMessage? {
            return message?.copy(message = "返回结果:${message.message}")
        }

        override fun connect() {
            ymwyLog("进行连接")
        }
    }

    override fun onBind(intent: Intent): IBinder {
        ymwyLog("service onBind ...")
        return mBinder
    }
}
