package com.liaction.ymwy.myapplication.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.liaction.ymwy.ymwyLog

class YMWYBindService : Service() {

    private val ymwyBinder = YMWYBinder()

    override fun onBind(intent: Intent): IBinder {
        return ymwyBinder
    }

    override fun onCreate() {
        ymwyLog("service on create")
        super.onCreate()
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
        ymwyLog("on destroy")
        super.onDestroy()
    }
}

class YMWYBinder : Binder() {
    fun send(message: String): String {
        return "收到信息:$message"
    }
}
