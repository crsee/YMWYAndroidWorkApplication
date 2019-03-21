package com.liaction.ymwy.myapplication.act

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.view.View
import com.liaction.ymwy.isNotNull
import com.liaction.ymwy.isNull
import com.liaction.ymwy.myapplication.R
import com.liaction.ymwy.myapplication.base.YMWYBaseActivity
import com.liaction.ymwy.myapplication.service.YMWYBindService
import com.liaction.ymwy.myapplication.service.YMWYMessengerService
import com.liaction.ymwy.ymwyLog
import com.liaction.ymwy.ymwyToast
import kotlinx.android.synthetic.main.activity_ymwyipcaidl2.*

class YMWYMessengerServiceActivity : YMWYBaseActivity() {

    private val mHandler = MessengerHandler(::updateTip)

    private val mMessenger = Messenger(mHandler)
    private var mIpcMessenger: Messenger? = null
    private val mServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            ymwyLog("service disconnected")
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            mIpcMessenger = Messenger(service)
            ymwyLog("service connected")
        }

    }
    private val message = StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ymwyipcaidl2)
        initAction()
    }

    private fun initAction() {
        mBindBtn.setOnClickListener {
            bindService(Intent(this, YMWYMessengerService::class.java), mServiceConnection, Context.BIND_AUTO_CREATE)
            ymwyToast(this, "绑定成功")
        }
        mUnbindBtn.setOnClickListener {
            if (mIpcMessenger.isNull()) {
                // 还没有进行绑定,直接提示成功
                ymwyToast(this, "操作成功")
                return@setOnClickListener
            }
            unbindService(mServiceConnection)
            mIpcMessenger = null
            ymwyToast(this, "解绑成功")
        }
        mSendBtn.setOnClickListener {
            if (mIpcMessenger.isNull()) {
                ymwyToast(this, "请先进行绑定")
                return@setOnClickListener
            }
            val text = mMessageEt.text.toString().trim()
            if (text.isEmpty() || text.isBlank()) {
                ymwyToast(this, "请输入有效信息")
                return@setOnClickListener
            }
            // send message
            updateTip(text)
            mIpcMessenger?.let {
                with(it) {
                    send(Message.obtain().apply {
                        data = Bundle().apply {
                            putString("message", text)
                        }
                        replyTo = mMessenger
                    })
                }
            }
        }
    }

    private fun updateTip(text: String, send: Boolean = true) {
        message.append("${if (send) "发送" else "接收"}消息:\t\t$text\n")
        mResultTv.text = message.toString()
        mResultSv.fullScroll(View.FOCUS_DOWN)
    }

    override fun onDestroy() {
        if (mIpcMessenger.isNotNull()) {
            unbindService(mServiceConnection)
        }
        super.onDestroy()
    }
}

class MessengerHandler(val callback: (String, Boolean) -> Unit) : Handler() {
    override fun handleMessage(msg: Message?) {
        super.handleMessage(msg)
        msg?.let {
            callback(msg.data.getString("message") ?: "", true)
        }
    }
}
