package com.example.mywanandroid.data.model

data class ArticleList(
    val curPage: Int,
    val pageCount: Int,
    val size: Int,
    val total: Int,
    val datas: List<Article>
)

data class Article(
    val title: String,
    val userId: String,
    val superChapterName: String,
    val chapterName: String,
    val niceDate: String,
    val shareUser: String,
    val author: String,
    val link: String
)