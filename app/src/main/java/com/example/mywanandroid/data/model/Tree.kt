package com.example.mywanandroid.data.model

data class Tree(
    val name: String,
    val courseId: Int,
    val id: Int,
    val children: List<TreeChild>
)

data class TreeChild(
    val id: Int,
    val name: String,
) : java.io.Serializable