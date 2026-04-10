package com.example.mywanandroid.ui.home

import android.content.Context
import android.view.ViewGroup
import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.viewholder.QuickViewHolder
import com.example.mywanandroid.data.model.Article

class ArticleAdapter: BaseQuickAdapter<Article,QuickViewHolder>() {
    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): QuickViewHolder {

    }

    override fun onBindViewHolder(
        holder: QuickViewHolder,
        position: Int,
        item: Article?
    ) {

    }
}