package com.liaction.ymwy.myapplication.service

import android.app.Service
import android.content.Intent
import android.os.*
import com.liaction.ymwy.ymwyLog

class YMWYMessengerService : Service() {

    private val ymwyBinder = Messenger(MessengerHandler())

    override fun onBind(intent: Intent): IBinder {
        return ymwyBinder.binder
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

class MessengerHandler : Handler() {
    override fun handleMessage(msg: Message?) {
        super.handleMessage(msg)
        msg?.let {
            val reply = it.replyTo
            val message = Message.obtain()
            message.data = Bundle().apply {
                putString("message", "收到消息:${it.data.getString("message")}")
            }
            reply.send(message)
        }
    }
}
