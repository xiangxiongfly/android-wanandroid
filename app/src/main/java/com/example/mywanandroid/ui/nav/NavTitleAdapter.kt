package com.example.mywanandroid.ui.nav

import android.content.Context
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.viewholder.QuickViewHolder
import com.example.mywanandroid.R
import com.example.mywanandroid.data.model.Nav

class NavTitleAdapter : BaseQuickAdapter<Nav, QuickViewHolder>() {
    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): QuickViewHolder {
        return QuickViewHolder(R.layout.item_nav_title, parent)
    }

    override fun onBindViewHolder(
        holder: QuickViewHolder,
        position: Int,
        item: Nav?
    ) {
        item?.let {
            holder.setText(R.id.tv_title, it.name)
            holder.setVisible(R.id.view_line, it.selected)
            holder.setBackgroundColor(
                R.id.ll_wrap,
                if (it.selected) ContextCompat.getColor(context, R.color.white)
                else ContextCompat.getColor(context, R.color.nav_title_bg)
            )
        }
    }
}