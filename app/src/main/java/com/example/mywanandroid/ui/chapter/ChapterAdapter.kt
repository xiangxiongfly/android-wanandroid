package com.example.mywanandroid.ui.chapter

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.setPadding
import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.viewholder.QuickViewHolder
import com.example.mywanandroid.common.ext.dp
import com.example.mywanandroid.common.utils.generateRandomColor
import com.example.mywanandroid.data.model.Chapter

class ChapterAdapter : BaseQuickAdapter<Chapter, QuickViewHolder>() {
    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): QuickViewHolder {
        val textView = TextView(context).apply {
            setPadding(50.dp)
            gravity = Gravity.CENTER
            setTextColor(Color.WHITE)
        }
        return QuickViewHolder(textView)
    }

    override fun onBindViewHolder(
        holder: QuickViewHolder,
        position: Int,
        item: Chapter?
    ) {
        item?.let {
            (holder.itemView as TextView).apply {
                text = it.name
                setBackgroundColor(generateRandomColor())
            }

        }
    }
}