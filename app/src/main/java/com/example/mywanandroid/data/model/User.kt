package com.example.mywanandroid.data.model

import com.google.gson.annotations.SerializedName

data class User(
    val username: String,
    @SerializedName("id") val userId: Long
)