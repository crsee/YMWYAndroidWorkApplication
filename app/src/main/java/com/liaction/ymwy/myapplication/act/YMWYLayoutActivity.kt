package com.liaction.ymwy.myapplication.act

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import com.liaction.ymwy.myapplication.base.YMWYBaseActivity
import com.liaction.ymwy.ymwyLog


class YMWYLayoutActivity : YMWYBaseActivity() {

    object INSTANCE {
        var fromThis = false
            private set

        fun start(context: Context) {
            fromThis = true
            context.startActivity(Intent(context, YMWYLayoutActivity::class.java))
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!INSTANCE.fromThis) {
            ymwyLog("you should start this activity through YMWYLayoutActivity.INSTANCE.start(..)")
            finish()
            return
        }
        val color = ContextCompat.getColor(this, com.liaction.ymwy.myapplication.R.color.colorAccent)
        val contentView = layoutInflater.inflate(com.liaction.ymwy.myapplication.R.layout.activity_ymwylayout, null)
        setContentView(contentView)
        contentView.setOnClickListener {
            addWindowView(color)
        }

        ymwyLog()
    }

    @SuppressLint("SetTextI18n")
    private fun addWindowView(color: Int) {
        val subView = LinearLayout(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            setBackgroundColor(Color.WHITE)
            setPadding(20)
            gravity = Gravity.CENTER
            addView(
                TextView(this@YMWYLayoutActivity).apply {
                    text = "sub Text"
                    textSize = 28f
                    setTextColor(color)
                }, LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            )
        }
        windowManager.addView(
            subView,
            WindowManager.LayoutParams().apply {
                //                format = PixelFormat.TRANSPARENT
                x = 20
                y = 100
                height = WindowManager.LayoutParams.WRAP_CONTENT
                width = WindowManager.LayoutParams.WRAP_CONTENT
                gravity = Gravity.BOTTOM
                flags = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
            }
        )

        subView.setOnClickListener {
            windowManager.removeView(subView)
        }
    }
}
