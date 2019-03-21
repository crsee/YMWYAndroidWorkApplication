package com.liaction.ymwy.myapplication.act

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.liaction.ymwy.myapplication.R
import com.liaction.ymwy.myapplication.base.YMWYBaseActivity
import com.liaction.ymwy.ymwyLog


class YMWYCreateActivity : YMWYBaseActivity() {

    companion object {
        var fromThis = false
            private set

        fun start(context: Context) {
            fromThis = true
            context.startActivity(Intent(context, YMWYCreateActivity::class.java))
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!fromThis) {
            ymwyLog("you should start this activity through YMWYCreateActivity.INSTANCE.start(..)")
            finish()
            return
        }
    }

    override fun onResume() {
        super.onResume()
        ymwyLog("activity create onResume")
        Handler().postDelayed({
            setContentView(R.layout.activity_ymwy_create)
        },3000)
    }

}
