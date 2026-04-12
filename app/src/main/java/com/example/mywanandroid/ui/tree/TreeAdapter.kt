package com.example.mywanandroid.ui.tree

import android.content.Context
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.viewholder.QuickViewHolder
import com.example.mywanandroid.R
import com.example.mywanandroid.data.model.Tree
import com.google.android.flexbox.FlexboxLayout

class TreeAdapter : BaseQuickAdapter<Tree, QuickViewHolder>() {
    override fun onCreateViewHolder(
        context: Context, parent: ViewGroup, viewType: Int
    ): QuickViewHolder {
        return QuickViewHolder(R.layout.item_tree, parent)
    }

    override fun onBindViewHolder(
        holder: QuickViewHolder, position: Int, item: Tree?
    ) {
        item?.let {
            holder.setText(R.id.tv_title, it.name)
            val fbl = holder.getView<FlexboxLayout>(R.id.fbl_tag)
            fbl.removeAllViews()
            for (tag in it.children) {
                val textView = TextView(context).apply {
                    text = tag.name
                    textSize = 12F
                    setTextColor(ContextCompat.getColor(context, R.color.tag))
                }
                fbl.addView(textView)
            }
        }
    }
}