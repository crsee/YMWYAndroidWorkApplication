package com.liaction.ymwy.myapplication.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.liaction.ymwy.myapplication.YMWYAidl
import com.liaction.ymwy.myapplication.bean.YMWYMessage2
import com.liaction.ymwy.ymwyLog

class YMWYAidlService2 : Service() {

    private val mBinder = object : YMWYAidl.Stub() {
        override fun send(message: YMWYMessage2?): YMWYMessage2? {
            return message?.copy(
                message = "已经收到了你的消息,内容 - ${message.message}"
            )
        }

    }

    override fun onCreate() {
        ymwyLog("service on create")
        super.onCreate()
    }

    override fun onBind(intent: Intent): IBinder {
        ymwyLog("on bind")
        return mBinder
    }

    override fun onRebind(intent: Intent?) {
        ymwyLog("on rebind")
        super.onRebind(intent)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        ymwyLog("on un bind")
        return true
    }

    override fun onDestroy() {
        ymwyLog("服务 on destroy")
        super.onDestroy()
    }
}
