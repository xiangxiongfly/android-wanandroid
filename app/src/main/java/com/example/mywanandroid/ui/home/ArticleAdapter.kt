package com.example.mywanandroid.ui.home

import android.content.Context
import android.text.Html
import android.text.TextUtils
import android.view.ViewGroup
import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.viewholder.QuickViewHolder
import com.example.mywanandroid.R
import com.example.mywanandroid.data.model.Article
import com.example.mywanandroid.ui.articles.ArticlesActivity.Companion.TYPE_COLLECTION

class ArticleAdapter : BaseQuickAdapter<Article, QuickViewHolder>() {
    private var type = -1

    fun setType(type: Int) {
        this.type = type
    }

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
            holder.setText(
                R.id.tv_super_chapter,
                if (!TextUtils.isEmpty(it.superChapterName) && !TextUtils.isEmpty(it.chapterName)) it.superChapterName + "•" + it.chapterName
                else if (!TextUtils.isEmpty(it.superChapterName)) it.superChapterName
                else if (!TextUtils.isEmpty(it.chapterName)) it.chapterName
                else ""
            )
            holder.setGone(R.id.tv_author, TextUtils.isEmpty(it.author) && TextUtils.isEmpty(it.shareUser))
            holder.setText(
                R.id.tv_author,
                if (TextUtils.isEmpty(it.author)) it.shareUser else it.author
            )
            holder.setText(R.id.tv_date, it.niceDate)
            if (type == TYPE_COLLECTION) {
                holder.setImageResource(R.id.iv_collect, R.drawable.ic_collected)
            } else {
                holder.setImageResource(
                    R.id.iv_collect,
                    if (it.collect) R.drawable.ic_collected else R.drawable.ic_unselect
                )
            }
        }
    }
}