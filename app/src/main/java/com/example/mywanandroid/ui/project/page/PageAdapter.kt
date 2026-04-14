package com.example.mywanandroid.ui.project.page

import android.content.Context
import android.text.Html
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import coil3.load
import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.viewholder.QuickViewHolder
import com.example.mywanandroid.R
import com.example.mywanandroid.data.model.ProjectArticle

class PageAdapter : BaseQuickAdapter<ProjectArticle, QuickViewHolder>() {
    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): QuickViewHolder {
        return QuickViewHolder(R.layout.item_project_article, parent)
    }

    override fun onBindViewHolder(
        holder: QuickViewHolder,
        position: Int,
        item: ProjectArticle?
    ) {
        item?.let {
            holder.setText(R.id.tv_title, it.title)
            holder.setText(R.id.tv_desc, Html.fromHtml(it.desc, Html.FROM_HTML_MODE_LEGACY))
            holder.setText(R.id.tv_author, it.author)
            holder.setText(R.id.tv_date, it.niceDate)
            holder.getView<ImageView>(R.id.iv).load(it.envelopePic) {
                listener(onError = { req, ret -> Log.e("TAG", "error ${req.data}") })
            }

        }
    }
}