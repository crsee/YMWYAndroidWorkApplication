package com.liaction.ymwy.myapplication.act

import android.net.Uri
import android.os.Bundle
import android.view.View
import com.liaction.ymwy.isNull
import com.liaction.ymwy.myapplication.R
import com.liaction.ymwy.myapplication.base.YMWYBaseActivity
import com.liaction.ymwy.ymwyToast
import kotlinx.android.synthetic.main.activity_ymwyipcaidl2.*

class YMWYContentProviderActivity : YMWYBaseActivity() {

    private val message = StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ymwyipcaidl2)
        initAction()
    }

    private fun initAction() {
        mBindBtn.visibility = View.GONE
        mUnbindBtn.visibility = View.GONE
        mSendBtn.setOnClickListener {
            val text = mMessageEt.text.toString().trim()
            if (text.isEmpty() || text.isBlank()) {
                ymwyToast(this, "请输入有效信息")
                return@setOnClickListener
            }
            // send message
            updateTip(text)
            val uri = Uri.parse("content://com.liaction.ymwy.myapplication.provider.YMWYContentProvider")
            val bundle = contentResolver.call(uri, "send", text, Bundle().apply {
                putString("name", "ymwy")
            })
            if (bundle.isNull()) {
                return@setOnClickListener
            }
            updateTip(
                "${bundle!!.getString("message")} - name:${bundle.getBundle("extras")?.getString("name")}",
                send = false
            )
        }
    }

    private fun updateTip(text: String, send: Boolean = true) {
        message.append("${if (send) "发送" else "接收"}消息:\t\t$text\n")
        mResultTv.text = message.toString()
        mResultSv.fullScroll(View.FOCUS_DOWN)
    }
}