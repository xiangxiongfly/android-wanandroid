package com.example.mywanandroid.data.model

data class Nav(
    val cid: Int,
    val name: String,
    val articles: List<NavArticle>
) {
    var selected = false
}

data class NavArticle(
    val title: String,
    val id: Int,
    val link: String
)