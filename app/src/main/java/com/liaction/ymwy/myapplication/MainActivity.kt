package com.liaction.ymwy.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.liaction.ymwy.isNotNull
import com.liaction.ymwy.myapplication.MainBeanType.*
import com.liaction.ymwy.myapplication.act.*
import com.liaction.ymwy.myapplication.base.YMWYBaseActivity
import com.liaction.ymwy.ymwyLog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : YMWYBaseActivity() {

    private val mData: MutableList<MainBaseBean> = mutableListOf(
        MainContentBean(
            name = "View Event",
            description = "view 事件传递",
            mainBeanType = TYPE_VIEW_EVENT
        ).apply {
            show = false
        },
        MainContentBean(
            name = "Activity Layout",
            description = "看看Activity Layout,能不能找点好东西",
            mainBeanType = TYPE_ACTIVITY_LAYOUT
        ).apply {
            show = false
        },
        MainContentBean(
            name = "Activity Create",
            description = "看看 onCreate",
            mainBeanType = TYPE_ACTIVITY_CREATE
        ).apply {
            show = false
        },
        MainTitleBean(
            name = "IPC 跨进程间通信"
        ),
        MainContentBean(
            name = "AIDL",
            description = "aidl 使用",
            mainBeanType = TYPE_IPC_AIDL
        ),
        MainContentBean(
            name = "AIDL Twice",
            description = "再来一次操作,同上面一样",
            mainBeanType = TYPE_IPC_AIDL_TWICE
        ).apply {
            show = true
        }, MainContentBean(
            name = "Bind Service",
            description = "测试 Bind Service",
            mainBeanType = TYPE_IPC_BIND_SERVICE
        ).apply {
            show = true
        }, MainContentBean(
            name = "",
            description = "",
            mainBeanType = TYPE_IPC_AIDL
        ).apply {
            show = false
        }
    )

    private fun onMainItemClick(mainBaseBean: MainBaseBean) {
        ymwyLog("$mainBaseBean")
        if (mainBaseBean is MainContentBean) {
            when (mainBaseBean.mainBeanType) {
                TYPE_VIEW_EVENT -> {
                }
                TYPE_ACTIVITY_LAYOUT -> {
                    YMWYLayoutActivity.start(this)
                }
                TYPE_NO -> {
                }
                TYPE_ACTIVITY_CREATE -> {
                    YMWYCreateActivity.start(this)
                }
                TYPE_IPC_AIDL -> {
                    startActivity(Intent(this, YMWYIPCAidlActivity::class.java))
                }
                TYPE_IPC_AIDL_TWICE -> {
                    startActivity(Intent(this, YMWYIPCAidl2Activity::class.java))
                }
                TYPE_IPC_BIND_SERVICE -> {
                    startActivity(Intent(this, YMWYBindServiceActivity::class.java))
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        with(mRecyclerView) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.HORIZONTAL))
            adapter = MainAdapter(this@MainActivity, mData, ::onMainItemClick)
        }
    }
}

private class MainAdapter(
    private val context: Context, private val data: MutableList<MainBaseBean>,
    private val onItemClickListener: (mainBaseBean: MainBaseBean) -> Unit
) : RecyclerView.Adapter<MainBaseViewHolder>() {

    enum class MainType(val type: Int) {
        TYPE_TITLE(0x05),
        TYPE_CONTENT(0x15)
    }

    init {
        // 处理不显示的
        data.removeAll { !it.show }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainBaseViewHolder {
        return when (viewType) {
            MainType.TYPE_CONTENT.type -> MainContentViewHolder(
                LayoutInflater.from(context).inflate(
                    R.layout.item_main,
                    parent,
                    false
                )
            )
            else -> MainTitleViewHolder(
                LayoutInflater.from(context).inflate(
                    R.layout.item_main_title,
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemViewType(position: Int): Int = when (data[position]) {
        is MainContentBean -> MainType.TYPE_CONTENT.type
        is MainTitleBean -> MainType.TYPE_TITLE.type
    }

    override fun getItemCount(): Int {
        return if (data.isNullOrEmpty()) 0 else data.size
    }

    override fun onBindViewHolder(holder: MainBaseViewHolder, position: Int) {
        val baseBean = data[position]
        if (holder is MainContentViewHolder) {
            if (baseBean is MainContentBean) {
                holder.mainItemTextView.text = baseBean.name
                holder.mItemSubTextView.text = baseBean.description
                holder.itemView.setOnClickListener {
                    if (onItemClickListener.isNotNull()) {
                        onItemClickListener(data[position])
                    }
                }
            }
            return
        }
        if (holder is MainTitleViewHolder) {
            if (baseBean is MainTitleBean) {
                holder.mItemTitle.text = baseBean.name
            }
        }
    }
}

private sealed class MainBaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

private class MainContentViewHolder(itemView: View) : MainBaseViewHolder(itemView) {
    val mainItemTextView: TextView = itemView.findViewById(R.id.mItemTextView)
    val mItemSubTextView: TextView = itemView.findViewById(R.id.mItemSubTextView)
}

private class MainTitleViewHolder(itemView: View) : MainBaseViewHolder(itemView) {
    val mItemTitle: TextView = itemView.findViewById(R.id.mItemTitle)
}

private sealed class MainBaseBean {
    var show: Boolean = true
}

private data class MainContentBean(
    val name: String = "",
    val description: String = "",
    val mainBeanType: MainBeanType = TYPE_NO
) : MainBaseBean()

private data class MainTitleBean(
    val name: String = ""
) : MainBaseBean()

private enum class MainBeanType {
    TYPE_NO,
    TYPE_VIEW_EVENT,
    TYPE_ACTIVITY_LAYOUT,
    TYPE_ACTIVITY_CREATE,
    TYPE_IPC_AIDL,
    TYPE_IPC_BIND_SERVICE,
    TYPE_IPC_AIDL_TWICE,
}