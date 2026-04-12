package com.example.mywanandroid.data.model

data class ProjectArticleList(
    val datas: List<ProjectArticle>
)

data class ProjectArticle(
    val title: String,
    val author: String,
    val desc: String,
    var envelopePic: String,
    val link: String,
    val niceDate: String,
)