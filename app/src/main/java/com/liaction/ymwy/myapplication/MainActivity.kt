package com.liaction.ymwy.myapplication

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.liaction.ymwy.isNotNull
import com.liaction.ymwy.ymwyLog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mData = mutableListOf(
        MainBean(name = "View Event", description = "view 事件传递")
    )

    private fun onMainItemClick(position: Int, mainBean: MainBean) {
        ymwyLog("$position - $mainBean")
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
    private val context: Context, private val data: List<MainBean>,
    private val onItemClickListener: (position: Int, mainBean: MainBean) -> Unit
) : RecyclerView.Adapter<MainViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(LayoutInflater.from(context).inflate(R.layout.item_main, parent, false))
    }

    override fun getItemCount(): Int {
        return if (data.isNullOrEmpty()) 0 else data.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        with(holder) {
            with(data[position]) {
                with(mainItemTextView) {
                    text = name
                }
                with(mItemSubTextView) {
                    text = description
                }
            }
            itemView.setOnClickListener {
                if (onItemClickListener.isNotNull()) {
                    onItemClickListener(position, data[position])
                }
            }
        }
    }
}

private class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val mainItemTextView: TextView = itemView.findViewById(R.id.mItemTextView)
    val mItemSubTextView: TextView = itemView.findViewById(R.id.mItemSubTextView)
}

private data class MainBean(val name: String = "", val description: String = "")