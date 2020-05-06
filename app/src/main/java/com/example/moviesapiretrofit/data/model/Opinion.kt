package com.example.moviesapiretrofit.data.model

data class Opinion(
    val id: Int,
    val user: String,
    val comment: String,
    val rate: Int,
    val movie_id: Int
)