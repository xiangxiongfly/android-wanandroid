package com.example.mywanandroid.ui.home

import android.content.Context
import android.text.Html
import android.text.TextUtils
import android.view.ViewGroup
import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.viewholder.QuickViewHolder
import com.example.mywanandroid.R
import com.example.mywanandroid.data.model.Article

class ArticleAdapter : BaseQuickAdapter<Article, QuickViewHolder>() {
    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): QuickViewHolder {
        return QuickViewHolder(R.layout.item_article, parent)
    }

    override fun onBindViewHolder(
        holder: QuickViewHolder,
        position: Int,
        item: Article?
    ) {
        item?.let {
            holder.setText(R.id.tv_title, Html.fromHtml(it.title, Html.FROM_HTML_MODE_LEGACY))
            holder.setText(R.id.tv_super_chapter, it.superChapterName)
            holder.setText(
                R.id.tv_author,
                if (TextUtils.isEmpty(it.author)) it.shareUser else it.author
            )
            holder.setText(R.id.tv_date, it.niceDate)
        }
    }
}