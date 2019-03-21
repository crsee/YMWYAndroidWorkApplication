package com.liaction.ymwy.myapplication.act

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import com.liaction.ymwy.isNull
import com.liaction.ymwy.myapplication.IYMWYAidlInterface
import com.liaction.ymwy.myapplication.R
import com.liaction.ymwy.myapplication.base.YMWYBaseActivity
import com.liaction.ymwy.myapplication.bean.YMWYMessage
import com.liaction.ymwy.myapplication.service.YMWYAidlService
import com.liaction.ymwy.ymwyLog
import com.liaction.ymwy.ymwyToast
import kotlinx.android.synthetic.main.activity_ymwyipcaidl.*

class YMWYIPCAidlActivity : YMWYBaseActivity(), ServiceConnection {
    // message
    private var mSendMessage = ""

    private var mBinder: IYMWYAidlInterface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ymwyipcaidl)
        initAction()
    }

    private fun initAction() {
        mAidlSendBtn.setOnClickListener {
            if (mBinder.isNull()) {
                ymwyToast(this, "请先进行连接")
                return@setOnClickListener
            }
            mAidlMessageTv.text = ""
            mSendMessage = mAidlMessageEt.text.toString().trim()
            if (mSendMessage.isNotEmpty() && mSendMessage.isNotBlank()) {
                mBinder?.sendMessage(YMWYMessage(mSendMessage))?.apply {
                    mAidlMessageTv.text = message
                }
                return@setOnClickListener
            }
            ymwyToast(this, "请输入消息内容")
        }
        mAidlConnectBtn.setOnClickListener {
            ymwyLog("准备连接")
            bindService(
                Intent(this, YMWYAidlService::class.java),
                this, Context.BIND_AUTO_CREATE
            )
        }
    }

    override fun onDestroy() {
        unbindService(this)
        super.onDestroy()
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        ymwyLog("连接断开了")
        mBinder = null
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        ymwyLog("连接成功")
        mBinder = IYMWYAidlInterface.Stub.asInterface(service)
    }
}
